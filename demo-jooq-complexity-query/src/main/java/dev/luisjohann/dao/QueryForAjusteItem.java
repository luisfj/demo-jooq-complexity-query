package dev.luisjohann.dao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.function.BiConsumer;

import org.jooq.CommonTableExpression;
import org.jooq.Field;
import org.jooq.Operator;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.jooq.TableLike;
import org.jooq.impl.DSL;
import org.springframework.util.function.ThrowingConsumer;

import dev.luisjohann.enums.TipoOperacaoAjuste;
import dev.luisjohann.jooq.Tables;
import dev.luisjohann.jooq.tables.AjusteItem;
import dev.luisjohann.util.NumberUtil;
import dev.luisjohann.vo.AjusteItemVO;

public class QueryForAjusteItem extends BaseQuery {

    private Field<Integer> fId;
    private Field<Integer> fAjusteId;
    private Field<Integer> fProdutoId;
    private Field<BigDecimal> fQuantidade;
    private Field<String> fTipoOperacao;

    protected AjusteItem tbAjusteItem;
    protected TableLike<?> tbRoot;

    protected BiConsumer<AjusteItemVO, Record> beanCollector;

    public Field<Integer> getAjusteId() {
        return externalField(fAjusteId);
    }

    public QueryForAjusteItem(EstoqueReadContext ctx, String alias) {
        super(ctx, alias);
        this.tbRoot = this.tbAjusteItem = Tables.AJUSTE_ITEM.as("tbRoot");
        this.beanCollector = (bean, rec) -> this.ctx.collectors.ajusteItemCollector.put(bean.getId(), bean);
    }

    public QueryForAjusteItem addExistByIdValues(Collection<Integer> ajusteItensIds) {
        ThrowingConsumer<SelectQuery<? extends Record>> oldFilter = this.filter;
        this.filter = (query) -> {
            oldFilter.accept(query);

            query.addConditions(Operator.OR, this.tbAjusteItem.ID.in(ajusteItensIds));
        };
        return this;
    }

    public QueryForAjusteItem addExistsByProdutoId(CommonTableExpression<? extends Record> vw,
            Field<Integer> produtoIdField) {
        this.existenceList.add(
                this.ctx.dsl
                        .select(DSL.val(1))
                        .from(vw)
                        .where()
                        .and(produtoIdField.eq(this.tbAjusteItem.PRODUTO_ID))
                        .getQuery());

        return this;
    }

    public QueryForAjusteItem saveOnEstoque() {
        BiConsumer<AjusteItemVO, Record> precBeanCollector = this.beanCollector;
        this.beanCollector = (bean, rec) -> {
            precBeanCollector.accept(bean, rec);
            this.ctx.addOnList(ctx.estoque::getItensAjustes, ctx.estoque::setItensAjustes, bean);
        };
        return this;
    }

    @Override
    public QueryForAjusteItem build() {
        this.fId = fInt(this.tbAjusteItem.ID);
        this.fAjusteId = fInt(this.tbAjusteItem.AJUSTE_ID);
        this.fProdutoId = fInt(this.tbAjusteItem.PRODUTO_ID);
        this.fQuantidade = fBigDec(this.tbAjusteItem.QUANTIDADE);
        this.fTipoOperacao = fStr(this.tbAjusteItem.TIPO_OPERACAO);

        if (this.view == null) {
            SelectQuery<? extends Record> query = this.ctx.dsl
                    .select(fields)
                    .from(this.tbRoot)
                    .getQuery();

            this.extendsQuery(query);

            if (!this.existenceList.isEmpty()) {
                query.addConditions(Operator.OR, buildExistQuery(this.existenceList));
            }

            this.filter.accept(query);
            this.view = DSL.name(this.alias).as(query);
        }
        return this;
    }

    @Override
    protected void leitorDeRegistro(Record rec) {
        AjusteItemVO bean = new AjusteItemVO();
        bean.setId(this.fId.get(rec));
        if (this.checkExistance && this.ctx.collectors.ajusteItemCollector.containsKey(bean.getId())) {
            return;
        }

        bean.setQuantidade(NumberUtil.convertToBigDecimal(this.fQuantidade.get(rec)));
        bean.setTipoOperacao(TipoOperacaoAjuste.valueOf(this.fTipoOperacao.get(rec)));

        Integer ajusteId = this.fAjusteId.get(rec);
        if (ajusteId != null) {
            this.ctx.collectors.ajusteCollector.push(ajusteId, bean::setAjuste);
        }

        Integer produtoId = this.fProdutoId.get(rec);
        if (produtoId != null) {
            this.ctx.collectors.produtoCollector.push(produtoId, bean::setProduto);
        }

        this.beanCollector.accept(bean, rec);
    }

}
