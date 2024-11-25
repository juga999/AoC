package aoc.twentythree.day1;

import aoc.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Day1 {

    protected Integer extractNumber(String str) {
        if (str.startsWith("one")) {
            return 1;
        } else if (str.startsWith("two")) {
            return 2;
        } else if (str.startsWith("three")) {
            return 3;
        } else if (str.startsWith("four")) {
            return 4;
        } else if (str.startsWith("five")) {
            return 5;
        } else if (str.startsWith("six")) {
            return 6;
        } else if (str.startsWith("seven")) {
            return 7;
        } else if (str.startsWith("eight")) {
            return 8;
        } else if (str.startsWith("nine")) {
            return 9;
        } else {
            return null;
        }
    }

    public Integer run(String dataPath) {
        List<Integer> digits = new ArrayList<>();
        List<String> lines = Day.getInputDataLines("2023", "day1", dataPath);
        for (String l : lines) {
            List<Integer> numbers = new ArrayList<>();
            int pos = 0;
            for (char c : l.toCharArray()) {
                if (Character.isDigit(c)) {
                    numbers.add(Character.getNumericValue(c));
                } else {
                    Optional.ofNullable(extractNumber(l.substring(pos))).ifPresent(numbers::add);
                }
                ++pos;
            }
            int value = Integer.parseInt("%d%d".formatted(numbers.getFirst() , numbers.getLast()));
            digits.add(value);
        }
        return digits.stream().reduce(Integer::sum).orElse(0);
    }

}
