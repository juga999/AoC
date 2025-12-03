package aoc.twentyfour.day12;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class Day12Tests {

    @Test
    void testsPart1() {
        {
            Day12 day12 = new Day12();
            day12.fillData("input_basic.txt");
            day12.buildAreas();
            /*long result = day12.getTotalPrice();
            Assertions.assertEquals(772, result);*/
            long result = day12.getPrice('O');
            Assertions.assertEquals(756, result);
        }
        /*{
            Day12 day12 = new Day12();
            day12.fillData("input_simple.txt");
            day12.buildAreas();
            long result = day12.getTotalPrice();
            Assertions.assertEquals(1930, result);
        }
        {
            Day12 day12 = new Day12();
            day12.fillData("input.txt");
            day12.buildAreas();
            long result = day12.getTotalPrice();
            // 1451966 to low
            Assertions.assertEquals(0, result);
        }*/
    }

}
