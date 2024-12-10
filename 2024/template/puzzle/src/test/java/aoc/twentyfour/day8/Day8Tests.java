package aoc.twentyfour.day8;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class Day8Tests {

    @Test
    void testsPart1() {
        {
            Day8 day8 = new Day8();
            day8.fillData("input_simple.txt");
            int result = day8.run1();
            Assertions.assertEquals(14, result);
        }
        {
            Day8 day8 = new Day8();
            day8.fillData("input.txt");
            int result = day8.run1();
            Assertions.assertEquals(254, result);
        }
    }

    @Test
    void testsPart2() {
        {
            Day8 day8 = new Day8();
            day8.fillData("input_basic.txt");
            int result = day8.run2();
            Assertions.assertEquals(9, result);
        }
        {
            Day8 day8 = new Day8();
            day8.fillData("input_simple.txt");
            int result = day8.run2();
            Assertions.assertEquals(34, result);
        }
        {
            Day8 day8 = new Day8();
            day8.fillData("input.txt");
            int result = day8.run2();
            Assertions.assertEquals(951L, result);
        }
    }
}
