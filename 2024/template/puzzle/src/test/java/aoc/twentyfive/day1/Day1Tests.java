package aoc.twentyfive.day1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day1Tests {

    @Test
    void testsPart1() {
        {
            Day1 day1 = new Day1();
            long result = day1.run1("input_simple.txt");
            assertEquals(3L, result);
        }
        {
            Day1 day1 = new Day1();
            long result = day1.run1("input.txt");
            assertEquals(1078L, result);
        }
    }

    @Test
    void testsPart2() {
        {
            Day1 day1 = new Day1();
            long result = day1.run2("input_simple.txt");
            assertEquals(6L, result);
        }
        {
            Day1 day1 = new Day1();
            long result = day1.run2("input.txt");
            assertEquals(6412L, result);
        }
    }
}
