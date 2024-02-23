/*
 * This file is generated by jOOQ.
 */
package dev.luisjohann.jooq.tables;


import dev.luisjohann.jooq.Keys;
import dev.luisjohann.jooq.Public;

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
public class Pessoa extends TableImpl<Record> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.pessoa</code>
     */
    public static final Pessoa PESSOA = new Pessoa();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>public.pessoa.id</code>.
     */
    public final TableField<Record, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.pessoa.nome</code>.
     */
    public final TableField<Record, String> NOME = createField(DSL.name("nome"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    private Pessoa(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private Pessoa(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.pessoa</code> table reference
     */
    public Pessoa(String alias) {
        this(DSL.name(alias), PESSOA);
    }

    /**
     * Create an aliased <code>public.pessoa</code> table reference
     */
    public Pessoa(Name alias) {
        this(alias, PESSOA);
    }

    /**
     * Create a <code>public.pessoa</code> table reference
     */
    public Pessoa() {
        this(DSL.name("pessoa"), null);
    }

    public <O extends Record> Pessoa(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, PESSOA);
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
        return Keys.PESSOA_PKEY;
    }

    @Override
    public List<UniqueKey<Record>> getKeys() {
        return Arrays.<UniqueKey<Record>>asList(Keys.PESSOA_PKEY);
    }

    @Override
    public Pessoa as(String alias) {
        return new Pessoa(DSL.name(alias), this);
    }

    @Override
    public Pessoa as(Name alias) {
        return new Pessoa(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Pessoa rename(String name) {
        return new Pessoa(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Pessoa rename(Name name) {
        return new Pessoa(name, null);
    }
}