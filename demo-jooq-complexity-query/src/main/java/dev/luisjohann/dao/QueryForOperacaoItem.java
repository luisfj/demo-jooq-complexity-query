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

import dev.luisjohann.jooq.Tables;
import dev.luisjohann.jooq.tables.OperacaoItem;
import dev.luisjohann.util.NumberUtil;
import dev.luisjohann.vo.OperacaoItemVO;

public class QueryForOperacaoItem extends BaseQuery {

    private Field<Integer> fId;
    private Field<Integer> fOperacaoId;
    private Field<Integer> fProdutoId;
    private Field<BigDecimal> fQuantidade;
    private Field<BigDecimal> fValorUn;

    protected OperacaoItem tbOperacaoItem;
    protected TableLike<?> tbRoot;

    protected BiConsumer<OperacaoItemVO, Record> beanCollector;

    public Field<Integer> getOperacaoId() {
        return externalField(fOperacaoId);
    }

    public QueryForOperacaoItem(EstoqueReadContext ctx, String alias) {
        super(ctx, alias);
        this.tbRoot = this.tbOperacaoItem = Tables.OPERACAO_ITEM.as("tbRoot");
        this.beanCollector = (bean, rec) -> this.ctx.collectors.operacaoItemCollector.put(bean.getId(), bean);
    }

    public QueryForOperacaoItem addExistByIdValues(Collection<Integer> operacaoItensIds) {
        ThrowingConsumer<SelectQuery<? extends Record>> oldFilter = this.filter;
        this.filter = (query) -> {
            oldFilter.accept(query);

            query.addConditions(Operator.OR, this.tbOperacaoItem.ID.in(operacaoItensIds));
        };
        return this;
    }

    public QueryForOperacaoItem addExistsByProdutoId(CommonTableExpression<? extends Record> vw,
            Field<Integer> produtoIdField) {
        this.existenceList.add(
                this.ctx.dsl
                        .select(DSL.val(1))
                        .from(vw)
                        .where()
                        .and(produtoIdField.eq(this.tbOperacaoItem.PRODUTO_ID))
                        .getQuery());

        return this;
    }

    public QueryForOperacaoItem saveOnEstoque() {
        BiConsumer<OperacaoItemVO, Record> precBeanCollector = this.beanCollector;
        this.beanCollector = (bean, rec) -> {
            precBeanCollector.accept(bean, rec);
            this.ctx.addOnList(ctx.estoque::getItensOperacoes, ctx.estoque::setItensOperacoes, bean);
        };
        return this;
    }

    @Override
    public QueryForOperacaoItem build() {
        this.fId = fInt(this.tbOperacaoItem.ID);
        this.fOperacaoId = fInt(this.tbOperacaoItem.OPERACAO_ID);
        this.fProdutoId = fInt(this.tbOperacaoItem.PRODUTO_ID);
        this.fQuantidade = fBigDec(this.tbOperacaoItem.QUANTIDADE);
        this.fValorUn = fBigDec(this.tbOperacaoItem.VALOR_UN);

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
        OperacaoItemVO bean = new OperacaoItemVO();
        bean.setId(this.fId.get(rec));
        if (this.checkExistance && this.ctx.collectors.operacaoItemCollector.containsKey(bean.getId())) {
            return;
        }

        bean.setQuantidade(NumberUtil.convertToBigDecimal(this.fQuantidade.get(rec)));
        bean.setValorUn(NumberUtil.convertToBigDecimal(this.fValorUn.get(rec)));

        Integer operacaoId = this.fOperacaoId.get(rec);
        if (operacaoId != null) {
            this.ctx.collectors.operacaoCollector.push(operacaoId, bean::setOperacao);
        }

        Integer produtoId = this.fProdutoId.get(rec);
        if (produtoId != null) {
            this.ctx.collectors.produtoCollector.push(produtoId, bean::setProduto);
        }

        this.beanCollector.accept(bean, rec);
    }

}
