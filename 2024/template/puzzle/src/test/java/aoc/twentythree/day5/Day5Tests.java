package aoc.twentythree.day5;

import org.apache.commons.lang3.Range;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class Day5Tests {

    @Test
    void testsDay5Part1() {
        {
            Day5 day5 = new Day5();
            day5.run("input_simple.txt");
            assertEquals(82, day5.getLocation(79));
            assertEquals(35, day5.getMinLocation());
        }
        {
            Day5 day5 = new Day5();
            day5.run("input.txt");
            assertEquals(218513636, day5.getMinLocation());
        }
    }

    @Test
    void testsDay5Part2() {
        {
            Day5 day5 = new Day5();
            day5.run("input_simple.txt");
            assertFalse(day5.seedRanges.isEmpty());
            Set<Range<Long>> destRanges = day5.getDestRanges(0, day5.seedRanges);
            for (int i =  1; i < 7; i++) {
                destRanges = day5.getDestRanges(i, destRanges);
            }
            Long minRange = destRanges.stream()
                    .mapToLong(Range::getMinimum)
                    .min().orElse(0L);
            assertEquals(46, minRange);
        }
        {
            Day5 day5 = new Day5();
            day5.run("input.txt");
            assertFalse(day5.seedRanges.isEmpty());
            Set<Range<Long>> destRanges = day5.getDestRanges(0, day5.seedRanges);
            for (int i =  1; i < 7; i++) {
                destRanges = day5.getDestRanges(i, destRanges);
            }
            Long minRange = destRanges.stream()
                    .mapToLong(Range::getMinimum)
                    .min().orElse(0L);
            assertEquals(81956384, minRange);
        }
    }
}
