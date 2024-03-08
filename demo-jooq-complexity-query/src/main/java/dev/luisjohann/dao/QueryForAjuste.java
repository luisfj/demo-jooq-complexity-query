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

import dev.luisjohann.jooq.Tables;
import dev.luisjohann.jooq.tables.Ajuste;
import dev.luisjohann.vo.AjusteVO;

public class QueryForAjuste extends BaseQuery {

    private Field<Integer> fId;
    private Field<LocalDateTime> fDataHora;
    private Field<Integer> fUsuarioId;

    protected Ajuste tbAjuste;
    protected TableLike<?> tbRoot;

    protected BiConsumer<AjusteVO, Record> beanCollector;

    public Field<Integer> getUsuarioId() {
        return externalField(fUsuarioId);
    }

    public QueryForAjuste(EstoqueReadContext ctx, String alias) {
        super(ctx, alias);
        this.tbRoot = this.tbAjuste = Tables.AJUSTE.as("tbRoot");
        this.beanCollector = (bean, rec) -> this.ctx.collectors.ajusteCollector.put(bean.getId(), bean);
    }

    public QueryForAjuste addExistByIdValues(Collection<Integer> ajustesIds) {
        ThrowingConsumer<SelectQuery<? extends Record>> oldFilter = this.filter;
        this.filter = (query) -> {
            oldFilter.accept(query);

            query.addConditions(Operator.OR, this.tbAjuste.ID.in(ajustesIds));
        };
        return this;
    }

    public QueryForAjuste addExistsByIdField(CommonTableExpression<? extends Record> vw,
            Field<Integer> ajusteIdField) {
        this.existenceList.add(
                this.ctx.dsl
                        .select(DSL.val(1))
                        .from(vw)
                        .where()
                        .and(ajusteIdField.eq(this.tbAjuste.ID))
                        .getQuery());

        return this;
    }

    public QueryForAjuste saveOnEstoque() {
        BiConsumer<AjusteVO, Record> precBeanCollector = this.beanCollector;
        this.beanCollector = (bean, rec) -> {
            precBeanCollector.accept(bean, rec);
            this.ctx.addOnList(ctx.estoque::getAjustes, ctx.estoque::setAjustes, bean);
        };
        return this;
    }

    @Override
    public QueryForAjuste build() {
        this.fId = fInt(this.tbAjuste.ID);
        this.fDataHora = fDateTime(this.tbAjuste.DATA_HORA);
        this.fUsuarioId = fInt(this.tbAjuste.USUARIO_ID);

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
        AjusteVO bean = new AjusteVO();
        bean.setId(this.fId.get(rec));
        if (this.checkExistance && this.ctx.collectors.ajusteCollector.containsKey(bean.getId())) {
            return;
        }

        bean.setDataHora(this.fDataHora.get(rec));

        Integer usuarioId = this.fUsuarioId.get(rec);
        if (usuarioId != null) {
            this.ctx.collectors.usuarioCollector.push(usuarioId, bean::setUsuario);
        }

        this.ctx.collectors.ajusteItemCollector.onReady((otherBean) -> {
            if (bean.equals(otherBean.getAjuste())) {
                this.ctx.addOnSet(bean::getItens, bean::setItens, otherBean);
                otherBean.setAjuste(null);
            }
        });

        this.beanCollector.accept(bean, rec);

    }

}
