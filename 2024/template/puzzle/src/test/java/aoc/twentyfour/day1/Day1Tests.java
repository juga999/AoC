package aoc.twentyfour.day1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day1Tests {

    @Test
    void testsDay1Part1() {
        {
            Day1 day1 = new Day1();
            Long result = day1.run1("input.txt");
            assertEquals(1660292L, result);
        }
    }

    @Test
    void testsDay1Part2() {
        {
            Day1 day1 = new Day1();
            Long result = day1.run2("input.txt");
            assertEquals(22776016L, result);
        }
    }
}
