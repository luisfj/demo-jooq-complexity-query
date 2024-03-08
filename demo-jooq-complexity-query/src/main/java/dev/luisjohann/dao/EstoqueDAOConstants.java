package dev.luisjohann.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.jooq.Field;
import org.jooq.impl.DSL;

public class EstoqueDAOConstants {
    private static int fldIdGen = 0;

    static Integer FLD_TB_ID = fldIdGen++;

    static Integer FLD_BOOL_A = fldIdGen++;
    static Integer FLD_BOOL_B = fldIdGen++;
    static Integer FLD_BOOL_C = fldIdGen++;
    final static Integer FLD_BOOL_LAST = fldIdGen - 1;

    static Integer FLD_I16_A = fldIdGen++;
    static Integer FLD_I16_B = fldIdGen++;
    static Integer FLD_I16_C = fldIdGen++;
    final static Integer FLD_I16_LAST = fldIdGen - 1;

    static Integer FLD_I32_A = fldIdGen++;
    static Integer FLD_I32_B = fldIdGen++;
    static Integer FLD_I32_C = fldIdGen++;
    final static Integer FLD_I32_LAST = fldIdGen - 1;

    static Integer FLD_I64_A = fldIdGen++;
    static Integer FLD_I64_B = fldIdGen++;
    static Integer FLD_I64_C = fldIdGen++;
    final static Integer FLD_I64_LAST = fldIdGen - 1;

    static Integer FLD_F64_A = fldIdGen++;
    static Integer FLD_F64_B = fldIdGen++;
    static Integer FLD_F64_C = fldIdGen++;
    final static Integer FLD_F64_LAST = fldIdGen - 1;

    static Integer FLD_STR_A = fldIdGen++;
    static Integer FLD_STR_B = fldIdGen++;
    static Integer FLD_STR_C = fldIdGen++;
    final static Integer FLD_STR_LAST = fldIdGen - 1;

    static Integer FLD_DT_A = fldIdGen++;
    static Integer FLD_DT_B = fldIdGen++;
    static Integer FLD_DT_C = fldIdGen++;
    final static Integer FLD_DT_LAST = fldIdGen - 1;

    static Integer FLD_DTT_A = fldIdGen++;
    static Integer FLD_DTT_B = fldIdGen++;
    static Integer FLD_DTT_C = fldIdGen++;
    final static Integer FLD_DTT_LAST = fldIdGen - 1;

    static Integer FLD_ODT_A = fldIdGen++;
    static Integer FLD_ODT_B = fldIdGen++;
    static Integer FLD_ODT_C = fldIdGen++;
    final static Integer FLD_ODT_LAST = fldIdGen - 1;

    static Integer FLD_BYTES_A = fldIdGen++;
    static Integer FLD_BYTES_B = fldIdGen++;
    static Integer FLD_BYTES_C = fldIdGen++;
    final static Integer FLD_BYTES_LAST = fldIdGen - 1;

    static String[] FLD_NAMES = new String[fldIdGen];
    static Field<?>[] FLD_NULLS = new Field<?>[fldIdGen];

    static {
        FLD_NAMES[FLD_TB_ID] = "tb_id";
        FLD_NULLS[FLD_TB_ID] = DSL.field(DSL.castNull(Integer.class)).as(FLD_NAMES[FLD_TB_ID]);

        for (int i = FLD_BOOL_A; i <= FLD_BOOL_LAST; i++) {
            String fldName = "bool_" + i;
            FLD_NAMES[i] = fldName;
            FLD_NULLS[i] = DSL.field(DSL.castNull(Boolean.class)).as(fldName);
        }

        for (int i = FLD_I16_A; i <= FLD_I16_LAST; i++) {
            String fldName = "i16_" + i;
            FLD_NAMES[i] = fldName;
            FLD_NULLS[i] = DSL.field(DSL.castNull(Short.class)).as(fldName);
        }

        for (int i = FLD_I32_A; i <= FLD_I32_LAST; i++) {
            String fldName = "i32_" + i;
            FLD_NAMES[i] = fldName;
            FLD_NULLS[i] = DSL.field(DSL.castNull(Integer.class)).as(fldName);
        }

        for (int i = FLD_I64_A; i <= FLD_I64_LAST; i++) {
            String fldName = "i64_" + i;
            FLD_NAMES[i] = fldName;
            FLD_NULLS[i] = DSL.field(DSL.castNull(Long.class)).as(fldName);
        }

        for (int i = FLD_F64_A; i <= FLD_F64_LAST; i++) {
            String fldName = "f64_" + i;
            FLD_NAMES[i] = fldName;
            FLD_NULLS[i] = DSL.field(DSL.castNull(Double.class)).as(fldName);
        }

        for (int i = FLD_STR_A; i <= FLD_STR_LAST; i++) {
            String fldName = "str_" + i;
            FLD_NAMES[i] = fldName;
            FLD_NULLS[i] = DSL.field(DSL.castNull(String.class)).as(fldName);
        }

        for (int i = FLD_DT_A; i <= FLD_DT_LAST; i++) {
            String fldName = "dt_" + i;
            FLD_NAMES[i] = fldName;
            FLD_NULLS[i] = DSL.field(DSL.castNull(LocalDate.class)).as(fldName);
        }

        for (int i = FLD_DTT_A; i <= FLD_DTT_LAST; i++) {
            String fldName = "dtt_" + i;
            FLD_NAMES[i] = fldName;
            FLD_NULLS[i] = DSL.field(DSL.castNull(LocalDateTime.class)).as(fldName);
        }

        for (int i = FLD_ODT_A; i <= FLD_ODT_LAST; i++) {
            String fldName = "odt_" + i;
            FLD_NAMES[i] = fldName;
            FLD_NULLS[i] = DSL.field(DSL.castNull(OffsetDateTime.class)).as(fldName);
        }

        for (int i = FLD_BYTES_A; i <= FLD_BYTES_LAST; i++) {
            String fldName = "bts_" + i;
            FLD_NAMES[i] = fldName;
            FLD_NULLS[i] = DSL.field(DSL.castNull(byte[].class)).as(fldName);
        }
    }

}
