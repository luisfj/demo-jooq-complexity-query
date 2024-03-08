package dev.luisjohann.util;

import java.math.BigDecimal;

public class NumberUtil {
    public static BigDecimal convertToBigDecimal(Object value) {
        if (value instanceof BigDecimal)
            return (BigDecimal) value;
        if (value instanceof Double)
            return BigDecimal.valueOf((Double) value);
        if (value instanceof Float)
            return BigDecimal.valueOf((Float) value);
        throw new RuntimeException("Erro, tipo de dados Bigdecimal inválido!");
    }
}
