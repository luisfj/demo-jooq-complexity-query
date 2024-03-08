package dev.luisjohann.dao;

import java.util.Collection;
import java.util.function.BiConsumer;

import org.jooq.Field;
import org.jooq.Operator;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.jooq.TableLike;
import org.jooq.impl.DSL;
import org.springframework.util.function.ThrowingConsumer;

import dev.luisjohann.jooq.Tables;
import dev.luisjohann.jooq.tables.Produto;
import dev.luisjohann.vo.ProdutoVO;

public class QueryForProduto extends BaseQuery {

    private Field<Integer> fId;
    private Field<String> fNome;
    private Field<String> fDescricao;

    protected Produto tbProduto;
    protected TableLike<?> tbRoot;

    protected BiConsumer<ProdutoVO, Record> beanCollector;

    public Field<Integer> getIdField() {
        return externalField(this.fId);
    }

    public QueryForProduto(EstoqueReadContext ctx, String alias) {
        super(ctx, alias);
        this.tbRoot = this.tbProduto = Tables.PRODUTO.as("tbRoot");
        this.beanCollector = (bean, rec) -> this.ctx.collectors.produtoCollector.put(bean.id(), bean);
    }

    public QueryForProduto filterProduto(Integer produtoId) {
        if (produtoId != null) {
            this.filter = query -> {
                query.addConditions(Operator.AND, this.tbProduto.ID.eq(produtoId));
            };
        }
        return this;
    }

    public QueryForProduto addExistByIdValues(Collection<Integer> produtosIds) {
        ThrowingConsumer<SelectQuery<? extends Record>> oldFilter = this.filter;
        this.filter = (query) -> {
            oldFilter.accept(query);

            query.addConditions(Operator.OR, this.tbProduto.ID.in(produtosIds));
        };
        return this;
    }

    public QueryForProduto saveOnEstoque() {
        BiConsumer<ProdutoVO, Record> precBeanCollector = this.beanCollector;
        this.beanCollector = (bean, rec) -> {
            precBeanCollector.accept(bean, rec);
            this.ctx.estoque.setProdutoVO(bean);
        };
        return this;
    }

    @Override
    public QueryForProduto build() {
        this.fId = fInt(this.tbProduto.ID);
        this.fNome = fStr(this.tbProduto.NOME);
        this.fDescricao = fStr(this.tbProduto.DESCRICAO);

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
        Integer id = this.fId.get(rec);
        if (this.checkExistance && this.ctx.collectors.produtoCollector.containsKey(id)) {
            return;
        }

        String nome = fNome.get(rec);
        String descricao = fDescricao.get(rec);

        ProdutoVO bean = new ProdutoVO(id, nome, descricao);

        this.beanCollector.accept(bean, rec);
    }

}
