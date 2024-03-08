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
import dev.luisjohann.jooq.tables.Pessoa;
import dev.luisjohann.vo.PessoaVO;

public class QueryForPessoa extends BaseQuery {

    private Field<Integer> fId;
    private Field<String> fNome;

    protected Pessoa tbPessoa;
    protected TableLike<?> tbRoot;

    protected BiConsumer<PessoaVO, Record> beanCollector;

    public QueryForPessoa(EstoqueReadContext ctx, String alias) {
        super(ctx, alias);
        this.tbRoot = this.tbPessoa = Tables.PESSOA.as("tbRoot");
        this.beanCollector = (bean, rec) -> this.ctx.collectors.pessoaCollector.put(bean.id(), bean);
    }

    public QueryForPessoa addExistByIdValues(Collection<Integer> pessoasIds) {
        ThrowingConsumer<SelectQuery<? extends Record>> oldFilter = this.filter;
        this.filter = (query) -> {
            oldFilter.accept(query);

            query.addConditions(Operator.OR, this.tbPessoa.ID.in(pessoasIds));
        };
        return this;
    }

    public QueryForPessoa saveOnEstoque() {
        BiConsumer<PessoaVO, Record> precBeanCollector = this.beanCollector;
        this.beanCollector = (bean, rec) -> {
            precBeanCollector.accept(bean, rec);
            this.ctx.addOnList(ctx.estoque::getPessoas, ctx.estoque::setPessoas, bean);
        };
        return this;
    }

    @Override
    public QueryForPessoa build() {
        this.fId = fInt(this.tbPessoa.ID);
        this.fNome = fStr(this.tbPessoa.NOME);

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
        if (this.checkExistance && this.ctx.collectors.pessoaCollector.containsKey(id)) {
            return;
        }

        String nome = fNome.get(rec);

        PessoaVO bean = new PessoaVO(id, nome);

        this.beanCollector.accept(bean, rec);
    }

}
