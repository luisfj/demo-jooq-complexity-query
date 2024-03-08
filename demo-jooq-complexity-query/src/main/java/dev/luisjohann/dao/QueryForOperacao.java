package dev.luisjohann.dao;

import java.time.LocalDateTime;
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

import dev.luisjohann.enums.TipoOperacao;
import dev.luisjohann.jooq.Tables;
import dev.luisjohann.jooq.tables.Operacao;
import dev.luisjohann.vo.OperacaoVO;

public class QueryForOperacao extends BaseQuery {

    private Field<Integer> fId;
    private Field<LocalDateTime> fDataHora;
    private Field<Integer> fPessoaId;
    private Field<Integer> fUsuarioId;
    private Field<String> fTipoOperacao;

    protected Operacao tbOperacao;
    protected TableLike<?> tbRoot;

    protected BiConsumer<OperacaoVO, Record> beanCollector;

    public Field<Integer> getPessoaId() {
        return externalField(fPessoaId);
    }

    public Field<Integer> getUsuarioId() {
        return externalField(fUsuarioId);
    }

    public QueryForOperacao(EstoqueReadContext ctx, String alias) {
        super(ctx, alias);
        this.tbRoot = this.tbOperacao = Tables.OPERACAO.as("tbRoot");
        this.beanCollector = (bean, rec) -> this.ctx.collectors.operacaoCollector.put(bean.getId(), bean);
    }

    public QueryForOperacao addExistByIdValues(Collection<Integer> operacoesIds) {
        ThrowingConsumer<SelectQuery<? extends Record>> oldFilter = this.filter;
        this.filter = (query) -> {
            oldFilter.accept(query);

            query.addConditions(Operator.OR, this.tbOperacao.ID.in(operacoesIds));
        };
        return this;
    }

    public QueryForOperacao addExistsByIdField(CommonTableExpression<? extends Record> vw,
            Field<Integer> operacaoIdField) {
        this.existenceList.add(
                this.ctx.dsl
                        .select(DSL.val(1))
                        .from(vw)
                        .where()
                        .and(operacaoIdField.eq(this.tbOperacao.ID))
                        .getQuery());

        return this;
    }

    public QueryForOperacao saveOnEstoque() {
        BiConsumer<OperacaoVO, Record> precBeanCollector = this.beanCollector;
        this.beanCollector = (bean, rec) -> {
            precBeanCollector.accept(bean, rec);
            this.ctx.addOnList(ctx.estoque::getOperacoes, ctx.estoque::setOperacoes, bean);
        };
        return this;
    }

    @Override
    public QueryForOperacao build() {
        this.fId = fInt(this.tbOperacao.ID);
        this.fDataHora = fDateTime(this.tbOperacao.DATA_HORA);
        this.fPessoaId = fInt(this.tbOperacao.PESSOA_ID);
        this.fUsuarioId = fInt(this.tbOperacao.USUARIO_ID);
        this.fTipoOperacao = fStr(this.tbOperacao.TIPO_OPERACAO);

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
        OperacaoVO bean = new OperacaoVO();
        bean.setId(this.fId.get(rec));
        if (this.checkExistance && this.ctx.collectors.operacaoCollector.containsKey(bean.getId())) {
            return;
        }

        bean.setDataHora(this.fDataHora.get(rec));
        bean.setTipoOperacao(TipoOperacao.valueOf(this.fTipoOperacao.get(rec)));

        Integer pessoaId = this.fPessoaId.get(rec);
        if (pessoaId != null) {
            this.ctx.collectors.pessoaCollector.push(pessoaId, bean::setPessoa);
        }

        Integer usuarioId = this.fUsuarioId.get(rec);
        if (usuarioId != null) {
            this.ctx.collectors.usuarioCollector.push(usuarioId, bean::setUsuario);
        }

        this.ctx.collectors.operacaoItemCollector.onReady((otherBean) -> {
            if (bean.equals(otherBean.getOperacao())) {
                this.ctx.addOnList(bean::getItens, bean::setItens, otherBean);
                otherBean.setOperacao(null);
            }
        });

        this.beanCollector.accept(bean, rec);
    }

}
