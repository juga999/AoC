package aoc.twentyfour.day2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day2Tests {

    @Test
    void testsDay1Part1() {
        {
            Day2 day2 = new Day2();
            Long result = day2.run1("input_simple.txt");
            assertEquals(2L, result);
        }
        {
            Day2 day2 = new Day2();
            Long result = day2.run1("input.txt");
            assertEquals(502L, result);
        }
    }

    @Test
    void testsDay1Part2() {
        {
            Day2 day2 = new Day2();
            Long result = day2.run2("input_simple.txt");
            assertEquals(4L, result);
        }
        {
            Day2 day2 = new Day2();
            Long result = day2.run2("input.txt");
            assertEquals(544L, result);
        }
    }
}
