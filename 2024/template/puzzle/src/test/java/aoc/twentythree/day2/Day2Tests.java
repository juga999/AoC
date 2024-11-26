package aoc.twentythree.day2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day2Tests {

    @Test
    void testsDay2() {
        Day2 day2 = new Day2();
        {
            Integer result1 = day2.run1("input_simple.txt");
            assertEquals(8, result1);
            Integer result2 = day2.run1("input.txt");
            assertEquals(2913, result2);
        }
        {
            Integer result1 = day2.run2("input_simple.txt");
            assertEquals(2286, result1);
            Integer result2 = day2.run2("input.txt");
            assertEquals(55593, result2);
        }
    }

}
