package aoc.twentyfour.day11;

import aoc.Day;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 {
    List<Long> stones = new ArrayList<>();
    Map<Pair<Long, Integer>, Long> resultsCache = new HashMap<>();

    public void fillData(String path) {
        String data = Day.getInputDataLines("2024", getClass(), path).getFirst();
        Arrays.stream(data.split(" ")).forEach(n -> {
            stones.add(Long.parseLong(n));
        });
    }

    public Pair<Long, Long> blink(long number) {
        String valueStr = ""+number;
        if (number == 0) {
            return Pair.of(1L, null);
        } else if (valueStr.length() % 2 == 0) {
            int half = valueStr.length() / 2;
            Long left = Long.parseLong(valueStr.substring(0, half));
            Long right = Long.parseLong(valueStr.substring(half));
            return Pair.of(left, right);
        } else {
            long newValue = number * 2024L;
            return Pair.of(newValue, null);
        }
    }

    public long countStones(long number, int depth) {
        if (resultsCache.containsKey(Pair.of(number, depth))) {
            long cached = resultsCache.get(Pair.of(number, depth));
            resultsCache.remove(Pair.of(number, depth - 1));
            return cached;
        }
        Pair<Long, Long> stones = blink(number);
        if (depth == 1) {
            if (stones.getRight() == null) {
                return 1L;
            } else {
                return 2L;
            }
        } else {
            long count = countStones(stones.getLeft(), depth - 1);
            if (stones.getRight() != null) {
                count += countStones(stones.getRight(), depth - 1);
            }
            resultsCache.put(Pair.of(number, depth), count);
            return count;
        }
    }

    public long run(int depth) {
        long result = 0;
        for (Long value : stones) {
            result += countStones(value, depth);
        }
        return result;
    }
}
