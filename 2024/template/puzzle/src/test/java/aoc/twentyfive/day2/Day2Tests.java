package aoc.twentyfive.day2;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day2Tests {

    @Test
    void testsPart1() {
        assertTrue(Day2.isInvalidId(11));
        assertTrue(Day2.isInvalidId(22));
        assertTrue(Day2.isInvalidId(1188511885));
        {
            Day2 day2 = new Day2();
            long result = day2.run1("input_simple.txt");
            assertEquals(1227775554L, result);
        }
        {
            Day2 day2 = new Day2();
            long result = day2.run1("input.txt");
            assertEquals(30608905813L, result);
        }
    }

    @Test
    void testsPart2() {
        assertTrue(Day2.isComplexInvalidId(565656));
        assertTrue(Day2.isComplexInvalidId(824824824));
        {
            List<Long> ids = Day2.getComplexInvalidIds(11, 22);
            assertEquals(2, ids.size());
        }
        {
            List<Long> ids = Day2.getComplexInvalidIds(1188511880, 1188511890);
            assertEquals(1, ids.size());
        }
        {
            List<Long> ids = Day2.getComplexInvalidIds(565653, 565659);
            assertEquals(1, ids.size());
        }
        {
            Day2 day2 = new Day2();
            long result = day2.run2("input_simple.txt");
            assertEquals(4174379265L, result);
        }
        {
            Day2 day2 = new Day2();
            long result = day2.run2("input.txt");
            assertEquals(31898925685L, result);
        }
    }
}
