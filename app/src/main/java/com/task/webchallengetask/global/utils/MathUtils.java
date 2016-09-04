package com.task.webchallengetask.global.utils;

import java.math.BigDecimal;


public final class MathUtils {
    public static float round(float number, int decimalPlace) {
        BigDecimal roundNumber = new BigDecimal(Float.toString(number));
        roundNumber = roundNumber.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return roundNumber.floatValue();
    }
}
