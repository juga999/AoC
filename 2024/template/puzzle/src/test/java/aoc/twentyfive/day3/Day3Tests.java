package aoc.twentyfive.day3;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day3Tests {

    @Test
    void testPart1() {
        {
            long joltage = Day3.getJoltage("818181911112111");
            assertEquals(92L, joltage);
        }
        {
            long joltage = Day3.getJoltage(List.of(
                    "987654321111111",
                    "811111111111119",
                    "234234234234278",
                    "818181911112111"));
            assertEquals(357, joltage);
        }

        Day3 day3 = new Day3();
        long result = day3.run1("input.txt");
        assertEquals(17324L, result);
    }

    @Test
    void testPart2() {
        {
            long joltage = Day3.getJoltage("818181911112111", 12);
            assertEquals(888911112111L, joltage);
        }
        {
            long joltage = Day3.getJoltage(List.of(
                    "987654321111111",
                    "811111111111119",
                    "234234234234278",
                    "818181911112111"), 12);
            assertEquals(3121910778619L, joltage);
        }
        Day3 day3 = new Day3();
        long result = day3.run2("input.txt");
        assertEquals(171846613143331L, result);
    }
}
