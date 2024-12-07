package aoc.twentyfour.day7;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class Day7Tests {

    @Test
    void testsPart1() {
        {
            Day7.Equation e = new Day7.Equation("292: 11 6 16 20");
            Assertions.assertTrue(e.isValidTwoOperands());
        }
        {
            Day7.Equation e = new Day7.Equation("3267: 81 40 27");
            Assertions.assertTrue(e.isValidTwoOperands());
        }
        {
            Day7 day7 = new Day7();
            long result = day7.run1("input_simple.txt");
            Assertions.assertEquals(3749, result);
        }
        {
            Day7 day7 = new Day7();
            long result = day7.run1("input.txt");
            Assertions.assertEquals(1298300076754L, result);
        }
    }

    @Test
    void testsPart2() {
        {
            Day7.Equation e = new Day7.Equation("7290: 6 8 6 15");
            Assertions.assertTrue(e.isValidThreeOperands());
        }
        /*{
            Day7.Equation e = new Day7.Equation("156: 15 6");
            Assertions.assertTrue(e.isValidThreeOperands());
        }
        {
            Day7.Equation e = new Day7.Equation("83: 17 5");
            Assertions.assertFalse(e.isValidThreeOperands());
        }
        {
            Day7.Equation e = new Day7.Equation("190: 10 19");
            Assertions.assertTrue(e.isValidThreeOperands());
        }
        {
            Day7.Equation e = new Day7.Equation("3267: 81 40 27");
            Assertions.assertTrue(e.isValidThreeOperands());
        }
        {
            Day7.Equation e = new Day7.Equation("7290: 6 8 6 15");
            Assertions.assertTrue(e.isValidThreeOperands());
        }
        {
            Day7.Equation e = new Day7.Equation("192: 17 8 14");
            Assertions.assertTrue(e.isValidThreeOperands());
        }

        {
            Day7 day7 = new Day7();
            long result = day7.run2("input_simple.txt");
            Assertions.assertEquals(11387, result);
        }*/
    }
}
