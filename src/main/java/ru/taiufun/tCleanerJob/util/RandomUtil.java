package ru.taiufun.tCleanerJob.util;

import java.util.OptionalInt;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {

    public static OptionalInt tryParseRandomOrFixed(String input) {
        try {
            if (input.contains("r")) {
                String[] parts = input.split("r");
                int min = Integer.parseInt(parts[0]);
                int max = Integer.parseInt(parts[1]);
                if (min > max) {
                    int temp = min;
                    min = max;
                    max = temp;
                }
                return OptionalInt.of(ThreadLocalRandom.current().nextInt(min, max + 1));
            } else {
                return OptionalInt.of(Integer.parseInt(input));
            }
        } catch (Exception e) {
            return OptionalInt.empty();
        }
    }
}
