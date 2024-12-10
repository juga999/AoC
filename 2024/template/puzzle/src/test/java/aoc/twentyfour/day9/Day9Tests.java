package aoc.twentyfour.day9;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day9Tests {

    @Test
    void testsPart1() {
        {
            Day9 day9 = new Day9();
            day9.init("input_simple.txt");
            Integer slot = day9.getNextAvailableSlot(0);
            day9.moveFile(day9.getFiles().get(9), slot);
            slot = day9.getNextAvailableSlot(slot);
            day9.moveFile(day9.getFiles().get(8), slot);
        }
        {
            Day9 day9 = new Day9();
            day9.init("input_simple.txt");
            Long result = day9.run1();
            Assertions.assertEquals(1928, result);
        }
        {
            Day9 day9 = new Day9();
            day9.init("input.txt");
            Long result = day9.run1();
            Assertions.assertEquals(6430446922192L, result);
        }
    }

    @Test
    void testsPart2() {
        {
            Day9 day9 = new Day9();
            day9.init("input_simple.txt");
            Long result = day9.run2();
            Assertions.assertEquals(2858, result);
        }
        {
            Day9 day9 = new Day9();
            day9.init("input.txt");
            Long result = day9.run2();
            Assertions.assertEquals(6460170593016L, result);
        }
    }
}
