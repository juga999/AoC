package aoc.twentythree.day5;

import aoc.Day;
import org.apache.commons.lang3.Range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5 {

    static final Pattern pattern = Pattern.compile("(\\d+)\\s+(\\d+)\\s+(\\d+)");

    List<Long> seeds = new ArrayList<>();
    Set<Range<Long>> seedRanges = new HashSet<>();
    List<FarmingStep> farmingSteps = new ArrayList<>();

    public static class FarmingRangeMap implements Comparable<FarmingRangeMap> {
        final Range<Long> srcRange;
        final Range<Long> destRange;

        public FarmingRangeMap(long src, long dest, long size) {
            srcRange = Range.of(src, src + size - 1);
            destRange = Range.of(dest, dest + size - 1);
        }

        public boolean srcOverlaps(Range<Long> range) {
            return srcRange.isOverlappedBy(range);
        }

        @Override
        public int compareTo(FarmingRangeMap other) {
            return srcRange.getMinimum().compareTo(other.srcRange.getMinimum());
        }
    }

    public static class FarmingStep {
        TreeSet<FarmingRangeMap> rangeMaps = new TreeSet<>();

        void addRangeMap(String str) {
            Matcher m = pattern.matcher(str.trim());
            if (m.matches()) {
                addRangeMap(
                        Long.parseLong(m.group(2)),
                        Long.parseLong(m.group(1)),
                        Long.parseLong(m.group(3)));
            }
        }

        void addRangeMap(long src, long dest, long size) {
            rangeMaps.add(new FarmingRangeMap(src, dest, size));
        }

        long getDest(long src) {
            FarmingRangeMap map = rangeMaps.stream()
                    .filter(m -> m.srcRange.contains(src))
                    .findFirst()
                    .orElse(null);
            if (map != null) {
                long offset = src - map.srcRange.getMinimum();
                return map.destRange.getMinimum() + offset;
            } else {
                return src;
            }
        }

        Set<Range<Long>> getDestRanges(Set<Range<Long>> ranges) {
            Set<Range<Long>> result = new HashSet<>();
            ranges.forEach(r -> {
                result.addAll(getDestRanges(r));
            });
            return result;
        }

        Set<Range<Long>> getDestRanges(Range<Long> range) {
            Set<Range<Long>> result = new HashSet<>();
            FarmingRangeMap first = rangeMaps.getFirst();
            FarmingRangeMap last = rangeMaps.getLast();
            if (range.getMaximum() < first.srcRange.getMinimum() || range.getMinimum() > last.srcRange.getMaximum()) {
                result.add(range);
                return result;
            }

            for (FarmingRangeMap map : rangeMaps) {
                if (map.srcOverlaps(range)) {
                    if (map.srcRange.getMinimum() > range.getMinimum()) {
                        Range<Long> left = Range.of(range.getMinimum(), map.srcRange.getMinimum() - 1);
                        result.add(left);
                    }
                    Range<Long> intersection = map.srcRange.intersectionWith(range);
                    long offset = intersection.getMinimum() - map.srcRange.getMinimum();
                    long size = intersection.getMaximum() - intersection.getMinimum() + 1;
                    result.add(Range.of(map.destRange.getMinimum() + offset, map.destRange.getMinimum() + offset + size - 1));

                    if (range.getMaximum() > map.srcRange.getMaximum()) {
                        range = Range.of(map.srcRange.getMaximum() + 1, range.getMaximum());
                    }
                }
            }
            if (range.getMinimum() > rangeMaps.getLast().srcRange.getMaximum()) {
                result.add(range);
            }
            return result;
        }
    }

    public long getDest(int step, long src) {
        return farmingSteps.get(step).getDest(src);
    }

    public Set<Range<Long>> getDestRanges(int step, Set<Range<Long>> ranges) {
        return farmingSteps.get(step).getDestRanges(ranges);
    }

    public long getLocation(long src) {
        long next = src;
        for (FarmingStep step : farmingSteps) {
            next = step.getDest(next);
        }
        return next;
    }

    public void parseDiscretSeeds(String str) {
        Arrays.stream((str.split(":"))[1].split(" "))
                .filter(s -> !s.isBlank())
                .forEach(s -> seeds.add(Long.parseLong(s.trim())));
    }

    public void parseSeedRanges(String str) {
        List<Long> values = Arrays.stream((str.split(":"))[1].split(" "))
                .filter(s -> !s.isBlank())
                .map(Long::parseLong)
                .toList();
        int i = 0;
        while (i < values.size()) {
            Range<Long> range = Range.of(values.get(i), values.get(i) + values.get(i+1) - 1);
            seedRanges.add(range);
            i += 2;
        }
    }

    public void run(String dataPath) {
        List<String> lines = Day.getInputDataLines("2023", "day5", dataPath);
        FarmingStep farmingStep = null;
        for (String l : lines) {
            if (l.startsWith("seeds")) {
                parseDiscretSeeds(l);
                parseSeedRanges(l);
            } else if (l.contains(":")) {
                if (farmingStep != null) {
                    farmingSteps.add(farmingStep);
                }
                farmingStep = new FarmingStep();
            } else if (farmingStep != null) {
                farmingStep.addRangeMap(l);
            }
        }
        farmingSteps.add(farmingStep);
    }

    public long getMinLocation() {
        return seeds.stream().mapToLong(this::getLocation).min().orElse(0);
    }
}
