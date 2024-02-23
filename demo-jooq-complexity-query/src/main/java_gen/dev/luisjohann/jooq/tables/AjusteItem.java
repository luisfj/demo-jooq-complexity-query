/*
 * This file is generated by jOOQ.
 */
package dev.luisjohann.jooq.tables;


import dev.luisjohann.jooq.Keys;
import dev.luisjohann.jooq.Public;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.jooq.Check;
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
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AjusteItem extends TableImpl<Record> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.ajuste_item</code>
     */
    public static final AjusteItem AJUSTE_ITEM = new AjusteItem();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>public.ajuste_item.id</code>.
     */
    public final TableField<Record, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.ajuste_item.ajuste_id</code>.
     */
    public final TableField<Record, Integer> AJUSTE_ID = createField(DSL.name("ajuste_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.ajuste_item.produto_id</code>.
     */
    public final TableField<Record, Integer> PRODUTO_ID = createField(DSL.name("produto_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.ajuste_item.quantidade</code>.
     */
    public final TableField<Record, BigDecimal> QUANTIDADE = createField(DSL.name("quantidade"), SQLDataType.NUMERIC(18, 2).nullable(false), this, "");

    /**
     * The column <code>public.ajuste_item.tipo_operacao</code>.
     */
    public final TableField<Record, String> TIPO_OPERACAO = createField(DSL.name("tipo_operacao"), SQLDataType.CHAR(1).nullable(false), this, "");

    private AjusteItem(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private AjusteItem(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.ajuste_item</code> table reference
     */
    public AjusteItem(String alias) {
        this(DSL.name(alias), AJUSTE_ITEM);
    }

    /**
     * Create an aliased <code>public.ajuste_item</code> table reference
     */
    public AjusteItem(Name alias) {
        this(alias, AJUSTE_ITEM);
    }

    /**
     * Create a <code>public.ajuste_item</code> table reference
     */
    public AjusteItem() {
        this(DSL.name("ajuste_item"), null);
    }

    public <O extends Record> AjusteItem(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, AJUSTE_ITEM);
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
        return Keys.AJUSTE_ITEM_PKEY;
    }

    @Override
    public List<UniqueKey<Record>> getKeys() {
        return Arrays.<UniqueKey<Record>>asList(Keys.AJUSTE_ITEM_PKEY);
    }

    @Override
    public List<ForeignKey<Record, ?>> getReferences() {
        return Arrays.<ForeignKey<Record, ?>>asList(Keys.AJUSTE_ITEM__AJUSTE_ITEM_AJUSTE_ID_FKEY, Keys.AJUSTE_ITEM__AJUSTE_ITEM_PRODUTO_ID_FKEY);
    }

    private transient Ajuste _ajuste;
    private transient Produto _produto;

    public Ajuste ajuste() {
        if (_ajuste == null)
            _ajuste = new Ajuste(this, Keys.AJUSTE_ITEM__AJUSTE_ITEM_AJUSTE_ID_FKEY);

        return _ajuste;
    }

    public Produto produto() {
        if (_produto == null)
            _produto = new Produto(this, Keys.AJUSTE_ITEM__AJUSTE_ITEM_PRODUTO_ID_FKEY);

        return _produto;
    }

    @Override
    public List<Check<Record>> getChecks() {
        return Arrays.<Check<Record>>asList(
              Internal.createCheck(this, DSL.name("tp_oper_entrada_saida_chk"), "((tipo_operacao = ANY (ARRAY['E'::bpchar, 'S'::bpchar])))", true)
        );
    }

    @Override
    public AjusteItem as(String alias) {
        return new AjusteItem(DSL.name(alias), this);
    }

    @Override
    public AjusteItem as(Name alias) {
        return new AjusteItem(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public AjusteItem rename(String name) {
        return new AjusteItem(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public AjusteItem rename(Name name) {
        return new AjusteItem(name, null);
    }
}
