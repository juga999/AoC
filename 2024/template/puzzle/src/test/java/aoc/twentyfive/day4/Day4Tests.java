package aoc.twentyfive.day4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day4Tests {

    @Test
    void testPart1() {
        {
            Day4 day4 = new Day4();
            long result = day4.run1("input_simple.txt");
            assertEquals(13L, result);
        }
        {
            Day4 day4 = new Day4();
            long result = day4.run1("input.txt");
            assertEquals(1518L, result);
        }
    }

    @Test
    void testPart2() {
        {
            Day4 day4 = new Day4();
            long result = day4.run2("input_simple.txt");
            assertEquals(43L, result);
        }
        {
            Day4 day4 = new Day4();
            long result = day4.run2("input.txt");
            assertEquals(8665L, result);
        }
    }
}
