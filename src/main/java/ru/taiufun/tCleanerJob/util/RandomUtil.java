package ru.taiufun.tCleanerJob.util;

import java.util.OptionalDouble;
import java.util.concurrent.ThreadLocalRandom;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class RandomUtil {

    public static OptionalDouble tryParseRandomOrFixed(String input, boolean isFloat) {
        try {
            if (input.contains("r")) {
                String[] parts = input.split("r");
                double min = Double.parseDouble(parts[0]);
                double max = Double.parseDouble(parts[1]);
                if (min > max) {
                    double temp = min;
                    min = max;
                    max = temp;
                }
                double result = isFloat
                        ? ThreadLocalRandom.current().nextDouble(min, max)
                        : ThreadLocalRandom.current().nextInt((int) min, (int) max + 1);
                if (isFloat) {
                    result = BigDecimal.valueOf(result).setScale(2, RoundingMode.HALF_UP).doubleValue();
                }
                return OptionalDouble.of(result);
            } else {
                double value = Double.parseDouble(input);
                if (isFloat) {
                    value = BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
                }
                return OptionalDouble.of(value);
            }
        } catch (Exception e) {
            return OptionalDouble.empty();
        }
    }
}
