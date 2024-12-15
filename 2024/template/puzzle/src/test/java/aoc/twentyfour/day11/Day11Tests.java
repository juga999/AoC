package aoc.twentyfour.day11;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class Day11Tests {

    @Test
    void testsPart1() {
        {
            Day11 day11 = new Day11();
            day11.fillData("input_simple.txt");
            long result = day11.run(25);
            Assertions.assertEquals(55312, result);
        }
        {
            Day11 day11 = new Day11();
            day11.fillData("input.txt");
            long result = day11.run(25);
            Assertions.assertEquals(183484, result);
        }
    }

    @Test
    void testsPart2() {
        {
            Day11 day11 = new Day11();
            day11.fillData("input.txt");
            long result = day11.run(75);
            Assertions.assertEquals(218817038947400L, result);
        }
    }
}
