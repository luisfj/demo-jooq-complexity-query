/*
 * This file is generated by jOOQ.
 */
package dev.luisjohann.jooq.tables;


import dev.luisjohann.jooq.Keys;
import dev.luisjohann.jooq.Public;

import java.math.BigDecimal;
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
public class OperacaoItem extends TableImpl<Record> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.operacao_item</code>
     */
    public static final OperacaoItem OPERACAO_ITEM = new OperacaoItem();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>public.operacao_item.id</code>.
     */
    public final TableField<Record, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.operacao_item.operacao_id</code>.
     */
    public final TableField<Record, Integer> OPERACAO_ID = createField(DSL.name("operacao_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.operacao_item.produto_id</code>.
     */
    public final TableField<Record, Integer> PRODUTO_ID = createField(DSL.name("produto_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.operacao_item.quantidade</code>.
     */
    public final TableField<Record, BigDecimal> QUANTIDADE = createField(DSL.name("quantidade"), SQLDataType.NUMERIC(18, 2).nullable(false), this, "");

    /**
     * The column <code>public.operacao_item.valor_un</code>.
     */
    public final TableField<Record, BigDecimal> VALOR_UN = createField(DSL.name("valor_un"), SQLDataType.NUMERIC(18, 2).nullable(false), this, "");

    private OperacaoItem(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private OperacaoItem(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.operacao_item</code> table reference
     */
    public OperacaoItem(String alias) {
        this(DSL.name(alias), OPERACAO_ITEM);
    }

    /**
     * Create an aliased <code>public.operacao_item</code> table reference
     */
    public OperacaoItem(Name alias) {
        this(alias, OPERACAO_ITEM);
    }

    /**
     * Create a <code>public.operacao_item</code> table reference
     */
    public OperacaoItem() {
        this(DSL.name("operacao_item"), null);
    }

    public <O extends Record> OperacaoItem(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, OPERACAO_ITEM);
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
        return Keys.OPERACAO_ITEM_PKEY;
    }

    @Override
    public List<UniqueKey<Record>> getKeys() {
        return Arrays.<UniqueKey<Record>>asList(Keys.OPERACAO_ITEM_PKEY);
    }

    @Override
    public List<ForeignKey<Record, ?>> getReferences() {
        return Arrays.<ForeignKey<Record, ?>>asList(Keys.OPERACAO_ITEM__OPERACAO_ITEM_OPERACAO_ID_FKEY, Keys.OPERACAO_ITEM__OPERACAO_ITEM_PRODUTO_ID_FKEY);
    }

    private transient Operacao _operacao;
    private transient Produto _produto;

    public Operacao operacao() {
        if (_operacao == null)
            _operacao = new Operacao(this, Keys.OPERACAO_ITEM__OPERACAO_ITEM_OPERACAO_ID_FKEY);

        return _operacao;
    }

    public Produto produto() {
        if (_produto == null)
            _produto = new Produto(this, Keys.OPERACAO_ITEM__OPERACAO_ITEM_PRODUTO_ID_FKEY);

        return _produto;
    }

    @Override
    public OperacaoItem as(String alias) {
        return new OperacaoItem(DSL.name(alias), this);
    }

    @Override
    public OperacaoItem as(Name alias) {
        return new OperacaoItem(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public OperacaoItem rename(String name) {
        return new OperacaoItem(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public OperacaoItem rename(Name name) {
        return new OperacaoItem(name, null);
    }
}
