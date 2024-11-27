package aoc.twentythree.day6;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day6Tests {

    @Test
    void testDay6Part1() {
        {
            long distance = Day6.getDistance(7, 0);
            assertEquals(distance, 0);
            long count1 = Day6.getWinningCount(7, 9);
            assertEquals(4, count1);
            long count2 = Day6.getWinningCount(15, 40);
            assertEquals(8, count2);
            long count3 = Day6.getWinningCount(30, 200);
            assertEquals(9, count3);
        }
        {
            long count1 = Day6.getWinningCount(59, 543);
            long count2 = Day6.getWinningCount(68, 1020);
            long count3 = Day6.getWinningCount(82, 1664);
            long count4 = Day6.getWinningCount(74, 1022);
            long result = count1 * count2 * count3 * count4;
            assertEquals(275724, result);
        }
    }

    @Test
    void testDay6Part2() {
        {
            long count = Day6.getWinningCount(71530, 940200);
            assertEquals(71503, count);
        }
        {
            long count = Day6.getWinningCount(59688274, new BigInteger("543102016641022"));
            assertEquals(37286485, count);
        }
    }
}
