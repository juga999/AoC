package aoc.twentyfour.day2;

import aoc.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day2 {

    String[] removeAt(String[] values, int pos) {
        String[] res = new String[values.length - 1];
        for (int i = 0, j = 0; i < values.length; i++) {
            if (i != pos) {
                res[j++] = values[i];
            }
        }
        return res;
    }

    public boolean isSafe(String[] values) {
        List<Long> deltas = new ArrayList<>();
        for (int i = 1; i < values.length; i++) {
            long d = Long.parseLong(values[i]) - Long.parseLong(values[i-1]);
            if (Math.abs(d) > 0 && Math.abs(d) < 4) {
                deltas.add(d);
            } else {
                return false;
            }
        }
        long incCount = deltas.stream().filter(d -> d > 0).count();
        long decCount = deltas.stream().filter(d -> d < 0).count();
        return !deltas.isEmpty() && (incCount <= 0 || decCount <= 0);
    }

    public boolean isSafeWithOneBad(String[] values) {
        if (isSafe(values)) {
            return true;
        }
        for (int i = 0; i < values.length; i++) {
            if (isSafe(removeAt(values, i))) {
                return true;
            }
        }
        return false;
    }

    Stream<String[]> getInputStream(String dataPath) {
        return Day.getInputDataStream("2024", getClass(), dataPath)
                .map(l -> l.split(" "));
    }

    public Long run1(String dataPath) {
        return getInputStream(dataPath)
                .filter(this::isSafe)
                .count();
    }

    public Long run2(String dataPath) {
        return getInputStream(dataPath)
                .filter(this::isSafeWithOneBad)
                .count();
    }
}
