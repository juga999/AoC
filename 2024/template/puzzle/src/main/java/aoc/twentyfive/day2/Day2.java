package aoc.twentyfive.day2;

import aoc.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2 {

    static final class Range {
        long start;
        long end;

        Range(String str) {
            String[] values = str.split("-");
            this.start = Long.parseLong(values[0]);
            this.end = Long.parseLong(values[1]);
        }
    }

    static boolean isInvalidId(long id) {
        String str = Long.toString(id);
        if (str.length() % 2 != 0) {
            return false;
        }
        String firstHalf = str.substring(0, str.length() / 2);
        String secondHalf = str.substring(str.length() / 2);
        return firstHalf.equalsIgnoreCase(secondHalf);
    }

    static boolean isComplexInvalidId(long id) {
        String str = Long.toString(id);
        for (int i = 1; i <= str.length(); i++) {
            if (str.length() % i == 0) {
                var values = str.split("(?<=\\G.{%d})".formatted(i));
                if (values.length > 1 && Arrays.stream(values)
                        .distinct()
                        .count() == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    static List<Long> getInvalidIds(long start, long end) {
        List<Long> result = new ArrayList<>();
        for (long i = start; i <= end; i++) {
            if (isInvalidId(i)) {
                result.add(i);
            }
        }
        return result;
    }

    static List<Long> getComplexInvalidIds(long start, long end) {
        List<Long> result = new ArrayList<>();
        for (long i = start; i <= end; i++) {
            if (isComplexInvalidId(i)) {
                result.add(i);
            }
        }
        return result;
    }

    List<Range> getInput(String dataPath) {
        return Arrays.stream(Day.getInputDataLines("2025", getClass(), dataPath)
                .getFirst()
                .split(","))
                .map(Range::new)
                .toList();
    }

    public long run1(String dataPath) {
        long result = 0;
        List<Range> ranges = getInput(dataPath);
        for (Range range : ranges) {
            result += getInvalidIds(range.start, range.end).stream().
                    mapToLong(Long::longValue)
                    .sum();
        }
        return result;
    }

    public long run2(String dataPath) {
        long result = 0;
        List<Range> ranges = getInput(dataPath);
        for (Range range : ranges) {
            result += getComplexInvalidIds(range.start, range.end).stream().
                    mapToLong(Long::longValue)
                    .sum();
        }
        return result;
    }
}
