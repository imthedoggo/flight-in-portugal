package tech.marcusvieira.flightinportugal.unit.utils;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import tech.marcusvieira.flightinportugal.utils.MathUtils;

public class MathUtilsTest {

    @Test
    void shouldDivideToBigDecimals() {
        BigDecimal value1 = new BigDecimal("6783432.76");
        BigDecimal value2 = new BigDecimal("3467");

        final BigDecimal result = MathUtils.divide(value1, value2);

        assertTrue(result.compareTo(new BigDecimal("1956.58")) == 0);
    }

    @Test
    void shouldNotDivideNull() {
        BigDecimal value1 = new BigDecimal("45.76");

        final BigDecimal result1 = MathUtils.divide(value1, null);
        assertNull(result1);

        final BigDecimal result2 = MathUtils.divide(null, value1);
        assertNull(result2);

        final BigDecimal result3 = MathUtils.divide(null, null);
        assertNull(result3);
    }

    @Test
    void shouldNotDivideZeroValue() {
        BigDecimal value1 = new BigDecimal("25433.09");

        final BigDecimal result1 = MathUtils.divide(value1, BigDecimal.ZERO);
        assertNull(result1);

        final BigDecimal result2 = MathUtils.divide(BigDecimal.ZERO, value1);
        assertNull(result2);

        final BigDecimal result3 = MathUtils.divide(BigDecimal.ZERO, BigDecimal.ZERO);
        assertNull(result3);
    }
}
