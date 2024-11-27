package aoc.twentythree.day7;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class Day7Tests {
    @Test
    void testsDay7Part1() {
        {
            Day7 day7 = new Day7();
            long winnings = day7.run1("input_simple.txt");
            assertEquals(6440, winnings);
        }
        {
            Day7 day7 = new Day7();
            long winnings = day7.run1("input.txt");
            assertEquals(250254244, winnings);
        }
    }

    @Test
    void testsDay7Part2() {
        {
            Day7 day7 = new Day7();
            long winnings = day7.run2("input_simple.txt");
            assertEquals(5905, winnings);
        }
        {
            Map<Day7.Card, Long> handMap = Map.of(Day7.Card.TWO, 1L, Day7.Card.THREE, 1L, Day7.Card.FOUR, 1L, Day7.Card.FIVE, 1L, Day7.Card.SIX, 1L);
            assertEquals(Day7.Type.HIGH_CARD, Day7.Hand.getType(handMap));
        }
        {
            Map<Day7.Card, Long> handMap = Map.of(Day7.Card.TWO, 1L, Day7.Card.THREE, 1L, Day7.Card.FOUR, 1L, Day7.Card.FIVE, 1L, Day7.Card.JOKER, 1L);
            assertEquals(Day7.Type.ONE_PAIR, Day7.Hand.getType(handMap));
        }
        {
            Map<Day7.Card, Long> handMap = Map.of(Day7.Card.TWO, 1L, Day7.Card.THREE, 1L, Day7.Card.FOUR, 1L, Day7.Card.JOKER, 2L);
            assertEquals(Day7.Type.THREES, Day7.Hand.getType(handMap));
        }
        {
            Map<Day7.Card, Long> handMap = Map.of(Day7.Card.TWO, 1L, Day7.Card.THREE, 1L, Day7.Card.JOKER, 3L);
            assertEquals(Day7.Type.FOURS, Day7.Hand.getType(handMap));
        }
        {
            Map<Day7.Card, Long> handMap = Map.of(Day7.Card.TWO, 1L, Day7.Card.JOKER, 4L);
            assertEquals(Day7.Type.FIVES, Day7.Hand.getType(handMap));
        }
        {
            Map<Day7.Card, Long> handMap = Map.of(Day7.Card.JOKER, 5L);
            assertEquals(Day7.Type.FIVES, Day7.Hand.getType(handMap));
        }

        {
            Map<Day7.Card, Long> handMap = Map.of(Day7.Card.TWO, 2L, Day7.Card.THREE, 1L, Day7.Card.FOUR, 1L, Day7.Card.FIVE, 1L);
            assertEquals(Day7.Type.ONE_PAIR, Day7.Hand.getType(handMap));
        }
        {
            Map<Day7.Card, Long> handMap = Map.of(Day7.Card.TWO, 2L, Day7.Card.THREE, 1L, Day7.Card.FOUR, 1L, Day7.Card.JOKER, 1L);
            assertEquals(Day7.Type.THREES, Day7.Hand.getType(handMap));
        }
        {
            Map<Day7.Card, Long> handMap = Map.of(Day7.Card.TWO, 2L, Day7.Card.THREE, 1L, Day7.Card.JOKER, 2L);
            assertEquals(Day7.Type.FOURS, Day7.Hand.getType(handMap));
        }
        {
            Map<Day7.Card, Long> handMap = Map.of(Day7.Card.TWO, 2L, Day7.Card.JOKER, 3L);
            assertEquals(Day7.Type.FIVES, Day7.Hand.getType(handMap));
        }

        {
            Map<Day7.Card, Long> handMap = Map.of(Day7.Card.TWO, 2L, Day7.Card.THREE, 2L, Day7.Card.FOUR, 1L);
            assertEquals(Day7.Type.TWO_PAIR, Day7.Hand.getType(handMap));
        }
        {
            Map<Day7.Card, Long> handMap = Map.of(Day7.Card.TWO, 2L, Day7.Card.THREE, 2L, Day7.Card.JOKER, 1L);
            assertEquals(Day7.Type.FULL_HOUSE, Day7.Hand.getType(handMap));
        }

        {
            Map<Day7.Card, Long> handMap = Map.of(Day7.Card.TWO, 3L, Day7.Card.THREE, 1L, Day7.Card.FOUR, 1L);
            assertEquals(Day7.Type.THREES, Day7.Hand.getType(handMap));
        }
        {
            Map<Day7.Card, Long> handMap = Map.of(Day7.Card.TWO, 3L, Day7.Card.THREE, 1L, Day7.Card.JOKER, 1L);
            assertEquals(Day7.Type.FOURS, Day7.Hand.getType(handMap));
        }

        {
            Map<Day7.Card, Long> handMap = Map.of(Day7.Card.TWO, 4L, Day7.Card.THREE, 1L);
            assertEquals(Day7.Type.FOURS, Day7.Hand.getType(handMap));
        }
        {
            Map<Day7.Card, Long> handMap = Map.of(Day7.Card.TWO, 4L, Day7.Card.JOKER, 1L);
            assertEquals(Day7.Type.FIVES, Day7.Hand.getType(handMap));
        }

        {
            Map<Day7.Card, Long> handMap = Map.of(Day7.Card.TWO, 5L);
            assertEquals(Day7.Type.FIVES, Day7.Hand.getType(handMap));
        }
        {

            Day7 day7 = new Day7();
            long winnings = day7.run2("input.txt");
            assertEquals(250087440, winnings);
        }
    }
}
