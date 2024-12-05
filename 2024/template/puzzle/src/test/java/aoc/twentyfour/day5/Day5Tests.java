package aoc.twentyfour.day5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Day5Tests {

    @Test
    void testsPart1() {
        {
            Day5 day5 = new Day5();
            day5.fillData("input_simple.txt", 100);
            Assertions.assertTrue(day5.isValid(List.of(75,47,61,53,29)));
            Assertions.assertTrue(day5.isValid(List.of(97,61,53,29,13)));
            Assertions.assertTrue(day5.isValid(List.of(75,29,13)));
            Assertions.assertFalse(day5.isValid(List.of(75,97,47,61,53)));
            Assertions.assertFalse(day5.isValid(List.of(61,13,29)));
            Assertions.assertFalse(day5.isValid(List.of(97,13,75,29,47)));
            Integer sum = day5.run1();
            Assertions.assertEquals(143, sum);
        }
        {
            Day5 day5 = new Day5();
            day5.fillData("input.txt", 100);
            Integer sum = day5.run1();
            Assertions.assertEquals(5639, sum);
        }
    }

    @Test
    void testsPart2() {
        {
            Day5 day5 = new Day5();
            day5.fillData("input_simple.txt", 100);
            Integer sum = day5.run2();
            Assertions.assertEquals(123, sum);
        }
        {
            Day5 day5 = new Day5();
            day5.fillData("input.txt", 100);
            Integer sum = day5.run2();
            Assertions.assertEquals(5273, sum);
        }
    }
}
