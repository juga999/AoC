package aoc.twentyfour.day12;

import aoc.CharMatrix2D;
import aoc.Day;
import aoc.Matrix2D;
import lombok.Getter;
import org.apache.commons.collections4.SetUtils;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.AsSubgraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
public class Day12 {

    CharMatrix2D matrix;
    Map<Character, Graph<Matrix2D.Location, DefaultEdge>> perimetersMap = new HashMap<>();
    Map<Character, Graph<Matrix2D.Location, DefaultEdge>> surfacesMap = new HashMap<>();

    public void addFence(Graph<Matrix2D.Location, DefaultEdge> area, Matrix2D.Location loc, Matrix2D.Direction dir) {
        Matrix2D.Location fromLoc;
        Matrix2D.Location toLoc;
        if (dir == Matrix2D.Direction.UP) {
            fromLoc = new Matrix2D.Location(loc.r(), loc.c());
            toLoc = new Matrix2D.Location(loc.r(), loc.c() + 1);
        } else if (dir == Matrix2D.Direction.DOWN) {
            fromLoc = new Matrix2D.Location(loc.r() + 1, loc.c());
            toLoc = new Matrix2D.Location(loc.r() + 1, loc.c() + 1);
        } else if (dir == Matrix2D.Direction.LEFT) {
            fromLoc = new Matrix2D.Location(loc.r(), loc.c());
            toLoc = new Matrix2D.Location(loc.r() + 1, loc.c());
        } else if (dir == Matrix2D.Direction.RIGHT) {
            fromLoc = new Matrix2D.Location(loc.r(), loc.c() + 1);
            toLoc = new Matrix2D.Location(loc.r() + 1, loc.c() + 1);
        } else {
            throw new RuntimeException("dir " + dir);
        }
        area.addVertex(fromLoc);
        area.addVertex(toLoc);
        area.addEdge(fromLoc, toLoc);
    }

    public void buildAreas() {
        matrix.locations().forEach(loc -> {
            Character value = matrix.get(loc);
            perimetersMap.computeIfAbsent(value, c -> new DefaultUndirectedGraph<>(DefaultEdge.class));
            surfacesMap.computeIfAbsent(value, c -> new DefaultUndirectedGraph<>(DefaultEdge.class));
            Graph<Matrix2D.Location, DefaultEdge> perimeter = perimetersMap.get(value);
            Graph<Matrix2D.Location, DefaultEdge> surface = surfacesMap.get(value);
            for (Matrix2D.Direction dir : Matrix2D.L_R_U_D_DIRS) {
                Matrix2D.Location otherLoc = matrix.getNeighborLocation(loc, dir);
                Character otherValue = matrix.charAt(otherLoc);
                if (otherValue == '\0' || otherValue != value) {
                    addFence(perimeter, loc, dir);
                } else {
                    if (!surface.containsEdge(loc, otherLoc)) {
                        surface.addVertex(loc);
                        surface.addVertex(otherLoc);
                        surface.addEdge(loc, otherLoc);
                    }
                }
            }
        });
    }

    public long getPrice(Character ch) {
        long result= 0L;
        Graph<Matrix2D.Location, DefaultEdge> areaGraph = getSurfacesMap().get(ch);
        Graph<Matrix2D.Location, DefaultEdge> perimeterGraph = getPerimetersMap().get(ch);
        ConnectivityInspector<Matrix2D.Location, DefaultEdge> areaGraphCi = new ConnectivityInspector<>(areaGraph);
        ConnectivityInspector<Matrix2D.Location, DefaultEdge> perimeterGraphCi = new ConnectivityInspector<>(perimeterGraph);
        for (Set<Matrix2D.Location> perimeterLocations : perimeterGraphCi.connectedSets()) {
            Graph<Matrix2D.Location, DefaultEdge> perimeterSubgraph = new AsSubgraph<>(perimeterGraph, perimeterLocations);
            Set<Matrix2D.Location> pSet = perimeterSubgraph.vertexSet();
            CharMatrix2D m = new CharMatrix2D(11, 11);
            m.fill(' ');
            m.fill('*', pSet);
            System.out.println(m);
            if (pSet.size() == 4) {
                result += 4;
            } else {
                for (Set<Matrix2D.Location> areaLocations : areaGraphCi.connectedSets()) {
                    Graph<Matrix2D.Location, DefaultEdge> areaSubgraph = new AsSubgraph<>(areaGraph, areaLocations);
                    Set<Matrix2D.Location> aSet = areaSubgraph.vertexSet();
                    if (!SetUtils.intersection(aSet, pSet).isEmpty()) {
                        result += areaSubgraph.vertexSet().size() * perimeterSubgraph.edgeSet().size();
                    }
                }
            }
        }

        return result;
    }

    public long getTotalPrice() {
        long result= 0L;
        for (Character ch : surfacesMap.keySet()) {
            long r = getPrice(ch);
            result += r;
        }
        return result;
    }

    public void fillData(String dataPath) {
        List<String> lines = Day.getInputDataLines("2024", getClass(), dataPath);
        matrix = new CharMatrix2D(lines);
    }

}
