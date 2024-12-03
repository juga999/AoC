package aoc.twentyfour.day3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day3Tests {

    @Test
    void testsPart1() {
        {
            Day3 day3 = new Day3();
            Long result = day3.run1("input_simple.txt");
            assertEquals(161L, result);
        }
        {
            Day3 day3 = new Day3();
            Long result = day3.run1("input.txt");
            assertEquals(174336360L, result);
        }
    }

    @Test
    void testsPart2() {
        {
            Day3 day3 = new Day3();
            Long result = day3.run2("input_simple2.txt");
            assertEquals(48L, result);
        }
        {
            Day3 day3 = new Day3();
            Long result = day3.run2("input.txt");
            assertEquals(88802350L, result);
        }
    }
}
