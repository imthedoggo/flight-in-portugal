package tech.marcusvieira.flightinportugal.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MathUtils {

    /**
     * Represents the {@link RoundingMode} used during calculations.
     */
    private static RoundingMode ROUNDING_MODE = RoundingMode.UP;

    /**
     * Represents the scale precision used during calculations.
     */
    private static int SCALE_PRECISION = 2;

    /**
     * Divides two {@link BigDecimal} using the {@link MathUtils#SCALE_PRECISION} and {@link MathUtils#ROUNDING_MODE}
     */
    public static BigDecimal divide(BigDecimal value1, BigDecimal value2) {
        if (value1 == null || value2 == null || value1.compareTo(BigDecimal.ZERO) == 0 ||
            value2.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        return value1.divide(value2, SCALE_PRECISION, ROUNDING_MODE);
    }
}
