package aoc.twentyfour.day10;

import aoc.Matrix2D;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day10Tests {

    @Test
    void testsPart1() {
        {
            Day10 day10 = new Day10();
            day10.fillData("input_basic.txt");
            Graph<Matrix2D.Location, DefaultEdge> path = day10.buildPath(new Matrix2D.Location(0, 3));
            Assertions.assertNotNull(path);
            Integer score = day10.getScoreMap().get(new Matrix2D.Location(0, 3));
            Assertions.assertEquals(2, score);
        }
        {
            Day10 day10 = new Day10();
            day10.fillData("input_simple.txt");
            Graph<Matrix2D.Location, DefaultEdge> path = day10.buildPath(new Matrix2D.Location(0, 2));
            Assertions.assertNotNull(path);
            Integer score = day10.getScoreMap().get(new Matrix2D.Location(0, 2));
            Assertions.assertEquals(5, score);
        }
        {
            Day10 day10 = new Day10();
            day10.fillData("input_simple.txt");
            Integer result = day10.run1();
            Assertions.assertEquals(36, result);
        }
        {
            Day10 day10 = new Day10();
            day10.fillData("input.txt");
            Integer result = day10.run1();
            Assertions.assertEquals(796, result);
        }
    }

    @Test
    void testsPart2() {
        {
            Day10 day10 = new Day10();
            day10.fillData("input_basic2.txt");
            Integer result = day10.run2();
            Assertions.assertEquals(3, result);
        }
        {
            Day10 day10 = new Day10();
            day10.fillData("input_simple.txt");
            Integer result = day10.run2();
            Assertions.assertEquals(81, result);
        }
        {
            Day10 day10 = new Day10();
            day10.fillData("input.txt");
            Integer result = day10.run2();
            Assertions.assertEquals(1942, result);
        }
    }
}
