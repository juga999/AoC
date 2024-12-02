package aoc.twentyfour.day1;

import aoc.Day;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day1 {

    Stream<String[]> getInputStream(String dataPath) {
        return Day.getInputDataStream("2024", getClass(), dataPath)
                .map(l -> l.split("   "));
    }

    public Long run1(String dataPath) {
        List<Long> leftList = new ArrayList<>();
        List<Long> rightList = new ArrayList<>();
        getInputStream(dataPath).forEach(values -> {
            leftList.add(Long.parseLong(values[0]));
            rightList.add(Long.parseLong(values[1]));
        });
        Collections.sort(leftList);
        Collections.sort(rightList);
        return IntStream.range(0, leftList.size())
                .mapToLong(i -> Math.abs(leftList.get(i) - rightList.get(i)))
                .sum();
    }

    public Long run2(String dataPath) {
        List<Long> leftList = new ArrayList<>();
        List<Long> rightList = new ArrayList<>();
        getInputStream(dataPath).forEach(values -> {
            leftList.add(Long.parseLong(values[0]));
            rightList.add(Long.parseLong(values[1]));
        });
        Map<Long, Long> rightMap = rightList.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return leftList.stream()
                .filter(rightMap::containsKey)
                .mapToLong(n -> n * rightMap.get(n))
                .sum();
    }
}
