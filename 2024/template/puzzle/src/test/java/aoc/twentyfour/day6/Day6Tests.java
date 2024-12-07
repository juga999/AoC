package aoc.twentyfour.day6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day6Tests {

    @Test
    void testsPart1() {
        {
            Day6 day6 = new Day6();
            day6.fillData("input_simple.txt");
            int result = day6.run1();
            Assertions.assertEquals(41, result);
        }
        {
            Day6 day6 = new Day6();
            day6.fillData("input.txt");
            int result = day6.run1();
            Assertions.assertEquals(5461, result);
        }
    }

    @Test
    void testsPart2() {
        {
            Day6 day6 = new Day6();
            day6.fillData("input_simple.txt");
            int result = day6.run2();
            Assertions.assertEquals(6, result);
        }
        {
            Day6 day6 = new Day6();
            day6.fillData("input.txt");
            int result = day6.run2();
            Assertions.assertEquals(1836, result);
        }
    }
}
