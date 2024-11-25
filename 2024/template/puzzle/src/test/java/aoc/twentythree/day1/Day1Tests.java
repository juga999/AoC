package aoc.twentythree.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day1Tests {

    @Test
    void testsDay1() {
        Day1 day1 = new Day1();
        Integer result1 = day1.run("input_simple.txt");
        assertEquals(281, result1);
        Integer result2 = day1.run("input.txt");
        assertEquals(54885, result2);
    }

}
