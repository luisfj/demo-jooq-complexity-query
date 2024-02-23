/*
 * This file is generated by jOOQ.
 */
package dev.luisjohann.jooq.tables;


import dev.luisjohann.jooq.Keys;
import dev.luisjohann.jooq.Public;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Ajuste extends TableImpl<Record> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.ajuste</code>
     */
    public static final Ajuste AJUSTE = new Ajuste();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>public.ajuste.id</code>.
     */
    public final TableField<Record, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.ajuste.data_hora</code>.
     */
    public final TableField<Record, LocalDateTime> DATA_HORA = createField(DSL.name("data_hora"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.ajuste.usuario_id</code>.
     */
    public final TableField<Record, Integer> USUARIO_ID = createField(DSL.name("usuario_id"), SQLDataType.INTEGER.nullable(false), this, "");

    private Ajuste(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private Ajuste(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.ajuste</code> table reference
     */
    public Ajuste(String alias) {
        this(DSL.name(alias), AJUSTE);
    }

    /**
     * Create an aliased <code>public.ajuste</code> table reference
     */
    public Ajuste(Name alias) {
        this(alias, AJUSTE);
    }

    /**
     * Create a <code>public.ajuste</code> table reference
     */
    public Ajuste() {
        this(DSL.name("ajuste"), null);
    }

    public <O extends Record> Ajuste(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, AJUSTE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public Identity<Record, Integer> getIdentity() {
        return (Identity<Record, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<Record> getPrimaryKey() {
        return Keys.AJUSTE_PKEY;
    }

    @Override
    public List<UniqueKey<Record>> getKeys() {
        return Arrays.<UniqueKey<Record>>asList(Keys.AJUSTE_PKEY);
    }

    @Override
    public List<ForeignKey<Record, ?>> getReferences() {
        return Arrays.<ForeignKey<Record, ?>>asList(Keys.AJUSTE__AJUSTE_USUARIO_ID_FKEY);
    }

    private transient Usuario _usuario;

    public Usuario usuario() {
        if (_usuario == null)
            _usuario = new Usuario(this, Keys.AJUSTE__AJUSTE_USUARIO_ID_FKEY);

        return _usuario;
    }

    @Override
    public Ajuste as(String alias) {
        return new Ajuste(DSL.name(alias), this);
    }

    @Override
    public Ajuste as(Name alias) {
        return new Ajuste(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Ajuste rename(String name) {
        return new Ajuste(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Ajuste rename(Name name) {
        return new Ajuste(name, null);
    }
}
