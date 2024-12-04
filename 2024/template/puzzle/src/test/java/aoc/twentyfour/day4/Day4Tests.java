package aoc.twentyfour.day4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day4Tests {

    @Test
    void testsPart1() {
        {
            Day4 day4 = new Day4();
            day4.fillData("input_simple.txt");
            Long result = day4.run1();
            Assertions.assertEquals(18, result);
        }
        {
            Day4 day4 = new Day4();
            day4.fillData("input.txt");
            Long result = day4.run1();
            Assertions.assertEquals(2524, result);
        }
    }

    @Test
    void testsPart2() {
        {
            Day4 day4 = new Day4();
            day4.fillData("input_simple.txt");
            Assertions.assertTrue(day4.hasXMas(1, 2));

            Long result = day4.run2();
            Assertions.assertEquals(9, result);
        }
        {
            Day4 day4 = new Day4();
            day4.fillData("input.txt");
            Long result = day4.run2();
            Assertions.assertEquals(1873, result);
        }
    }
}
