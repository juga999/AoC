package aoc.twentyfive.day5;

import aoc.Day;
import aoc.Range;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day5 {

    public long run1(String dataPath) {
        List<Range> ranges = new ArrayList<>();
        Set<Long> ids = new HashSet<>();

        Day.getInputDataStream("2025", getClass(), dataPath).forEach(l -> {
            if (l.contains("-")) {
                ranges.add(new Range(l));
            } else if (!l.isEmpty()) {
                ids.add(Long.parseLong(l));
            }
        });

        Set<Long> freshIds = new HashSet<>();
        for (Long id : ids) {
            for (Range range : ranges) {
                if (range.contains(id)) {
                    freshIds.add(id);
                }
            }
        }
        return freshIds.size();
    }

    public long run2(String dataPath) {
        List<Range> ranges = new ArrayList<>();
        List<Range> mergedRanges = new ArrayList<>();

        Day.getInputDataStream("2025", getClass(), dataPath).forEach(l -> {
            if (l.contains("-")) {
                ranges.add(new Range(l));
            }
        });

        ranges.sort(Comparator.comparing(Range::getStart));
        mergedRanges.addAll(Range.merge(ranges.get(0), ranges.get(1)));

        for (int i = 2; i < ranges.size(); i++) {
            List<Range> mergeResult = Range.merge(mergedRanges.getLast(), ranges.get(i));
            mergedRanges.removeLast();
            mergedRanges.addAll(mergeResult);
        }

        return mergedRanges.stream()
                .map(Range::size)
                .mapToLong(Long::longValue)
                .sum();
    }
}
