package aoc.twentyfive.day3;

import aoc.Day;

import java.util.ArrayList;
import java.util.List;

public class Day3 {

    static long getJoltage(String data) {
        return getJoltage(data, 2);
    }

    static long getJoltage(String data, int size) {
        List<String> values = new ArrayList<>();
        int fromPosition = 0;
        for (int j = size - 1; j >= 0; j--) {
            int maxValue = 0;
            for (int i = fromPosition; i < data.length() - j; i++) {
                int value = Integer.parseInt("" + data.charAt(i));
                if (value > maxValue) {
                    maxValue = value;
                    fromPosition = i+1;
                }
            }
            values.add(Integer.toString(maxValue));
        }
        return Long.parseLong(String.join("", values));
    }

    static long getJoltage(List<String> values) {
        return getJoltage(values, 2);
    }

    static long getJoltage(List<String> values, int size) {
        return values.stream()
                .map(v -> Day3.getJoltage(v, size))
                .mapToLong(Long::longValue)
                .sum();
    }

    public long run1(String dataPath) {
        return getJoltage(Day.getInputDataLines("2025", getClass(), dataPath));
    }

    public long run2(String dataPath) {
        return getJoltage(Day.getInputDataLines("2025", getClass(), dataPath), 12);
    }
}
