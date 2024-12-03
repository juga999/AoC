package aoc.twentyfour.day3;

import aoc.Day;

import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Day3 {

    public static final Pattern patternNoCondition = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
    public static final Pattern patternConditions = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)");

    boolean enabled = true;

    public Long compute(String line, Pattern pattern) {
        long sum = 0L;
        Scanner scanner = new Scanner(line);
        for (MatchResult r : scanner.findAll(pattern).toList()) {
            if ("do()".equals(r.group())) {
                enabled = true;
            } else if ("don't()".equals(r.group())) {
                enabled = false;
            } else if (enabled) {
                sum += (Long.parseLong(r.group(1)) * Long.parseLong(r.group(2)));
            }
        }
        return sum;
    }

    public Long run1(String dataPath) {
        return Day.getInputDataStream("2024", getClass(), dataPath)
                .mapToLong(l -> this.compute(l, patternNoCondition))
                .sum();
    }

    public Long run2(String dataPath) {
        return Day.getInputDataStream("2024", getClass(), dataPath)
                .mapToLong(l -> this.compute(l, patternConditions))
                .sum();
    }

}
