package aoc.twentyfour.day1;

import aoc.Day;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day1 {

    public Long run1(String dataPath) {
        List<Long> leftList = new ArrayList<>();
        List<Long> rightList = new ArrayList<>();
        List<String> lines = Day.getInputDataLines("2024", "day1", dataPath);
        for (String l : lines) {
            String[] values = l.split("   ");
            leftList.add(Long.parseLong(values[0]));
            rightList.add(Long.parseLong(values[1]));
        }
        Collections.sort(leftList);
        Collections.sort(rightList);
        long sum = 0L;
        for (int i = 0; i < lines.size(); i++) {
            sum += Math.abs(leftList.get(i) - rightList.get(i));
        }
        return sum;
    }

    public Long run2(String dataPath) {
        List<Long> leftList = new ArrayList<>();
        List<Long> rightList = new ArrayList<>();
        List<String> lines = Day.getInputDataLines("2024", "day1", dataPath);
        for (String l : lines) {
            String[] values = l.split("   ");
            leftList.add(Long.parseLong(values[0]));
            rightList.add(Long.parseLong(values[1]));
        }
        long similariyScore = 0L;
        Map<Long, Long> rightMap = rightList.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        for (Long number : leftList) {
            if (rightMap.containsKey(number)) {
                long score = number * rightMap.get(number);
                similariyScore += score;
            }
        }
        return similariyScore;
    }
}
