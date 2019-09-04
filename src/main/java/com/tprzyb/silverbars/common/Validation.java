package com.tprzyb.silverbars.common;

import java.math.BigDecimal;

public final class Validation {

    private Validation() { }

    public static void checkIsTrue(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    public static BigDecimal requireGreaterThanZero(BigDecimal value) {
        checkIsTrue(value.signum() == 1);
        return value;
    }

    public static String requireNonBlank(String value) {
        checkIsTrue(!value.trim().isEmpty());
        return value;
    }
}
