package aoc.twentyfive.day5;

import aoc.Range;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day5Tests {

    @Test
    void testPart1() {
        {
            Day5 day5 = new Day5();
            long result = day5.run1("input_simple.txt");
            assertEquals(3, result);
        }
        {
            Day5 day5 = new Day5();
            long result = day5.run1("input.txt");
            assertEquals(694, result);
        }
    }

    @Test
    void testPart2() {
        {
            List<Range> result = Range.merge(new Range(10, 14), new Range(12, 18));
            assertEquals(List.of(new Range(10, 18)), result);
        }
        {
            List<Range> result = Range.merge(new Range(10, 14), new Range(16, 20));
            assertEquals(List.of(new Range(10, 14), new Range(16, 20)), result);
        }
        {
            List<Range> result = Range.merge(new Range(12, 18), new Range(16, 20));
            assertEquals(List.of(new Range(12, 20)), result);
        }
        {
            Day5 day5 = new Day5();
            long result = day5.run2("input_simple.txt");
            assertEquals(14, result);
        }
        {
            Day5 day5 = new Day5();
            long result = day5.run2("input.txt");
            assertEquals(352716206375547L, result);
        }
    }
}
