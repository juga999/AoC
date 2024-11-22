package aoc.day0;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day0Tests {

    @Test
    void testsDay0() {
        Day0 day0 = new Day0();
        Integer result1 = day0.run("input_simple.txt");
        assertEquals(281, result1);
        Integer result2 = day0.run("input.txt");
        assertEquals(54885, result2);
    }

}
