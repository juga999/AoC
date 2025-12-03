package aoc.twentyfour.day10;

import aoc.CharMatrix2D;
import aoc.Day;
import aoc.Matrix2D;
import lombok.Getter;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
public class Day10 {

    CharMatrix2D matrix;
    Set<Matrix2D.Location> startingLocations = new HashSet<>();
    Set<Matrix2D.Location> endingLocations = new HashSet<>();
    Map<Matrix2D.Location, Integer> scoreMap = new HashMap<>();

    protected Integer getAt(Matrix2D.Location loc) {
        Character ch = matrix.charAt(loc);
        if (ch != '\0' && ch != '.') {
            return Day.toInt(ch);
        } else {
            return null;
        }
    }

    protected void incTrailHeadScore(Matrix2D.Location loc) {
        scoreMap.compute(loc, (k, score) -> score + 1);
    }

    public void fillData(String dataPath) {
        List<String> lines = Day.getInputDataLines("2024", getClass(), dataPath);
        matrix = new CharMatrix2D(lines);
        matrix.locations().forEach(loc -> {
            Integer value = getAt(loc);
            if (value != null) {
                if (value == 0) {
                    startingLocations.add(loc);
                } else if (value == 9) {
                    endingLocations.add(loc);
                }
            }
        });
    }

    public Graph<Matrix2D.Location, DefaultEdge> buildPath(Matrix2D.Location loc) {
        scoreMap.put(loc, 0);
        Graph<Matrix2D.Location, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        buildPath(graph, loc, loc);
        return graph;
    }

    public void buildPath(Graph<Matrix2D.Location, DefaultEdge> path, Matrix2D.Location startLoc, Matrix2D.Location loc) {
        Integer value = getAt(loc);
        for (Matrix2D.Direction dir : Matrix2D.L_R_U_D_DIRS) {
            Matrix2D.Location nextLoc = matrix.getNeighborLocation(loc, dir);
            Integer nextValue = getAt(nextLoc);
            if (nextValue != null && nextValue - value == 1) {
                path.addVertex(loc);
                boolean newLoc = path.addVertex(nextLoc);
                path.addEdge(loc, nextLoc);
                if (nextValue == 9 && newLoc) {
                    incTrailHeadScore(startLoc);
                } else {
                    buildPath(path, startLoc, nextLoc);
                }
            }
        }
    }

    public Integer run1() {
        startingLocations.forEach(this::buildPath);
        return scoreMap.values().stream().reduce(Integer::sum).orElse(0);
    }

    public Integer run2() {
        int result = 0;
        for (Matrix2D.Location startLoc : startingLocations) {
            Graph<Matrix2D.Location, DefaultEdge> path = buildPath(startLoc);
            AllDirectedPaths<Matrix2D.Location, DefaultEdge> allPaths = new AllDirectedPaths<>(path);
            for (Matrix2D.Location endingLoc : endingLocations) {
                if (path.containsVertex(endingLoc)) {
                    List<GraphPath<Matrix2D.Location, DefaultEdge>> match = allPaths.getAllPaths(startLoc, endingLoc, true, null);
                    result += match.size();
                }
            }
        }
        return result;
    }
}
