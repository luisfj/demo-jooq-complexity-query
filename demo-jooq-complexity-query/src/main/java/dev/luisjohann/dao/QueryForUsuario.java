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
import dev.luisjohann.jooq.tables.Usuario;
import dev.luisjohann.vo.UsuarioVO;

public class QueryForUsuario extends BaseQuery {

    private Field<Integer> fId;
    private Field<String> fNome;

    protected Usuario tbUsuario;
    protected TableLike<?> tbRoot;

    protected BiConsumer<UsuarioVO, Record> beanCollector;

    public QueryForUsuario(EstoqueReadContext ctx, String alias) {
        super(ctx, alias);
        this.tbRoot = this.tbUsuario = Tables.USUARIO.as("tbRoot");
        this.beanCollector = (bean, rec) -> this.ctx.collectors.usuarioCollector.put(bean.id(), bean);
    }

    public QueryForUsuario addExistByIdValues(Collection<Integer> usuariosIds) {
        ThrowingConsumer<SelectQuery<? extends Record>> oldFilter = this.filter;
        this.filter = (query) -> {
            oldFilter.accept(query);

            query.addConditions(Operator.OR, this.tbUsuario.ID.in(usuariosIds));
        };
        return this;
    }

    public QueryForUsuario saveOnEstoque() {
        BiConsumer<UsuarioVO, Record> precBeanCollector = this.beanCollector;
        this.beanCollector = (bean, rec) -> {
            precBeanCollector.accept(bean, rec);
            this.ctx.addOnList(ctx.estoque::getUsuarios, ctx.estoque::setUsuarios, bean);
        };
        return this;
    }

    @Override
    public QueryForUsuario build() {
        this.fId = fInt(this.tbUsuario.ID);
        this.fNome = fStr(this.tbUsuario.NOME);

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
        if (this.checkExistance && this.ctx.collectors.usuarioCollector.containsKey(id)) {
            return;
        }

        String nome = fNome.get(rec);

        UsuarioVO bean = new UsuarioVO(id, nome);

        this.beanCollector.accept(bean, rec);
    }

}
