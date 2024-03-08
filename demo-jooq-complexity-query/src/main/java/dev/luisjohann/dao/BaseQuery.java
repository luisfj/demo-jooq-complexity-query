package dev.luisjohann.dao;

import static dev.luisjohann.dao.EstoqueDAOConstants.FLD_NAMES;
import static dev.luisjohann.dao.EstoqueDAOConstants.FLD_NULLS;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jooq.CommonTableExpression;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Select;
import org.jooq.SelectJoinStep;
import org.jooq.SelectOrderByStep;
import org.jooq.SelectQuery;
import org.jooq.impl.DSL;
import org.springframework.util.function.ThrowingConsumer;

public abstract class BaseQuery {

    public CommonTableExpression<? extends Record> view;

    protected final EstoqueReadContext ctx;
    protected final String alias;
    protected final Integer tableId;
    protected final Field<?>[] fields;
    protected final Field<Integer> fTbId;

    protected ThrowingConsumer<SelectQuery<? extends Record>> filter;
    protected List<SelectQuery<Record1<Integer>>> existenceList;
    protected boolean checkExistance = true;

    private int boolFieldIdx = EstoqueDAOConstants.FLD_BOOL_A;
    private int shortFieldIdx = EstoqueDAOConstants.FLD_I16_A;
    private int intFieldIdx = EstoqueDAOConstants.FLD_I32_A;
    private int longFieldIdx = EstoqueDAOConstants.FLD_I64_A;
    private int doubleFieldIdx = EstoqueDAOConstants.FLD_F64_A;
    private int stringFieldIdx = EstoqueDAOConstants.FLD_STR_A;
    private int dateFieldIdx = EstoqueDAOConstants.FLD_DT_A;
    private int dateTimeFieldIdx = EstoqueDAOConstants.FLD_DTT_A;
    private int offsetDateTimeFieldIdx = EstoqueDAOConstants.FLD_ODT_A;
    private int byteFieldIdx = EstoqueDAOConstants.FLD_BYTES_A;

    private boolean allView = false;

    public BaseQuery(EstoqueReadContext ctx, String alias) {
        this.ctx = ctx;
        this.alias = alias;
        this.tableId = ctx.tableViewIdGen.incrementAndGet();
        this.fields = newEmptyFields();
        this.fTbId = field(DSL.value(this.tableId), EstoqueDAOConstants.FLD_TB_ID);
        this.filter = (p0) -> {
        };
        this.existenceList = new ArrayList<>();
        this.ctx.leitorDeRegistroMap.put(this.tableId, this::leitorDeRegistro);
    }

    public Integer getTableId() {
        return this.tableId;
    }

    public boolean isAllView() {
        return this.allView;
    }

    protected void doBuildAll(BaseQuery... queries) {
        SelectQuery<Record> query;

        SelectJoinStep<Record> first = this.ctx.dsl.select(DSL.asterisk()).from(queries[0].view);
        if (queries.length > 1) {
            SelectOrderByStep<Record> unionAll = first
                    .unionAll(this.ctx.dsl.select(DSL.asterisk()).from(queries[1].view));
            for (int i = 2; i < queries.length; i++) {
                unionAll = first.unionAll(this.ctx.dsl.select(DSL.asterisk()).from(queries[i].view));
            }
            query = unionAll.getQuery();
        } else {
            query = first.getQuery();
        }

        this.view = DSL.name(this.alias).as(query);

        this.build();

        this.allView = true;
        this.ctx.leitorDeRegistroMap.remove(this.tableId);
    }

    public abstract BaseQuery build();

    private Field<?>[] newEmptyFields() {
        Field<?>[] fields = new Field<?>[FLD_NULLS.length];
        System.arraycopy(FLD_NULLS, 0, fields, 0, fields.length);
        return fields;
    }

    private int checkIndex(String type, int idx, int maxIndex) {
        if (idx > maxIndex) {
            throw new RuntimeException(String.format("Index(%d) too big for %s", idx, type));
        }
        return idx;
    }

    protected Field<Boolean> fBool(Field<Boolean> fld) {
        return this.field(fld, checkIndex("bool", this.boolFieldIdx++, EstoqueDAOConstants.FLD_BOOL_LAST));
    }

    protected Field<Short> fShort(Field<Short> fld) {
        return this.field(fld, checkIndex("i16", this.shortFieldIdx++, EstoqueDAOConstants.FLD_I16_LAST));
    }

    protected Field<Integer> fInt(Field<Integer> fld) {
        return this.field(fld, checkIndex("i32", this.intFieldIdx++, EstoqueDAOConstants.FLD_I32_LAST));
    }

    protected Field<Long> fLong(Field<Long> fld) {
        return this.field(fld, checkIndex("i64", this.longFieldIdx++, EstoqueDAOConstants.FLD_I64_LAST));
    }

    protected Field<BigInteger> fBigInt(Field<BigInteger> fld) {
        return this.field(fld, checkIndex("i64", this.longFieldIdx++, EstoqueDAOConstants.FLD_I64_LAST));
    }

    protected Field<Float> fFloat(Field<Float> fld) {
        return this.field(fld, checkIndex("f64", this.doubleFieldIdx++, EstoqueDAOConstants.FLD_F64_LAST));
    }

    protected Field<Double> fDbl(Field<Double> fld) {
        return this.field(fld, checkIndex("f64", this.doubleFieldIdx++, EstoqueDAOConstants.FLD_F64_LAST));
    }

    protected Field<BigDecimal> fBigDec(Field<BigDecimal> fld) {
        return this.field(fld, checkIndex("f64", this.doubleFieldIdx++, EstoqueDAOConstants.FLD_F64_LAST));
    }

    protected Field<String> fStr(Field<String> fld) {
        return this.field(fld, checkIndex("str", this.stringFieldIdx++, EstoqueDAOConstants.FLD_STR_LAST));
    }

    protected Field<LocalDate> fDate(Field<LocalDate> fld) {
        return this.field(fld, checkIndex("dt", this.dateFieldIdx++, EstoqueDAOConstants.FLD_DT_LAST));
    }

    protected Field<LocalDateTime> fDateTime(Field<LocalDateTime> fld) {
        return this.field(fld, checkIndex("dtt", this.dateTimeFieldIdx++, EstoqueDAOConstants.FLD_DTT_LAST));
    }

    protected Field<OffsetDateTime> fOdt(Field<OffsetDateTime> fld) {
        return this.field(fld, checkIndex("odt", this.offsetDateTimeFieldIdx++, EstoqueDAOConstants.FLD_ODT_LAST));
    }

    protected Field<byte[]> fBytes(Field<byte[]> fld) {
        return this.field(fld, checkIndex("bts", this.byteFieldIdx++, EstoqueDAOConstants.FLD_BYTES_LAST));
    }

    protected <T> Field<T> field(Field<T> fld, int fieldIdx) {
        Field<T> asField = fld.as(FLD_NAMES[fieldIdx]);
        this.fields[fieldIdx] = asField;
        return asField;
    }

    protected <T> Field<T> externalField(Field<T> fld) {
        return DSL.field("\"" + this.alias + "\".\"" + fld.getName() + "\"", fld.getType());
    }

    protected Condition buildExistQuery(List<SelectQuery<Record1<Integer>>> entries) {
        Select<Record1<Integer>> result = entries.get(0);
        if (entries.size() > 1) {
            for (int i = 1; i < entries.size(); i++) {
                result = result.unionAll(entries.get(i));
            }
        }
        return DSL.exists(result);
    }

    protected void extendsQuery(SelectQuery<? extends Record> query) {

    }

    protected abstract void leitorDeRegistro(Record rec);
}
