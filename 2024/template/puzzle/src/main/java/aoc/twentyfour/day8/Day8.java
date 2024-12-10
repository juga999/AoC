package aoc.twentyfour.day8;

import aoc.CharMatrix2D;
import aoc.Day;
import aoc.Matrix2D;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
public class Day8 {

    CharMatrix2D matrix;
    Map<Character, List<Matrix2D.Location>> antennaMap = new HashMap<>();

    public Matrix2D.Location getOpposite(Matrix2D.Location centerLoc, Matrix2D.Location otherLoc) {
        int dr = otherLoc.r() - centerLoc.r();
        int dc = otherLoc.c() - centerLoc.c();
        Matrix2D.Location result = new Matrix2D.Location(centerLoc.r() - dr, centerLoc.c() - dc);
        if (getMatrix().isValidLocation(result)) {
            return result;
        } else {
            return null;
        }
    }

    public void fillData(String dataPath) {
        List<String> lines = Day.getInputDataLines("2024", getClass(), dataPath);
        matrix = new CharMatrix2D(lines);
        matrix.locations().forEach(l -> {
            Character ch = matrix.charAt(l);
            if (ch != '.') {
                antennaMap.putIfAbsent(ch, new ArrayList<>());
                antennaMap.get(ch).add(l);
            }
        });
    }

    public int run1() {
        Set<Matrix2D.Location> result = new HashSet<>();
        for (Character ch : antennaMap.keySet()) {
            List<Matrix2D.Location> locations = antennaMap.get(ch);
            for (int i = 0; i < locations.size(); i++) {
                for (int j = 0; j < locations.size(); j++) {
                    if (j != i) {
                        Matrix2D.Location loc = getOpposite(locations.get(i), locations.get(j));
                        if (loc != null) {
                            result.add(loc);
                        }
                    }
                }
            }
        }
        return result.size();
    }

    public int run2() {
        Set<Matrix2D.Location> result = new HashSet<>();
        for (Character ch : antennaMap.keySet()) {
            List<Matrix2D.Location> locations = antennaMap.get(ch);
            for (int i = 0; i < locations.size(); i++) {
                for (int j = 0; j < locations.size(); j++) {
                    if (j != i) {
                        {
                            int dr = locations.get(j).r() - locations.get(i).r();
                            int dc = locations.get(j).c() - locations.get(i).c();
                            int inc = 1;
                            Matrix2D.Location l = new Matrix2D.Location(locations.get(i).r() - dr, locations.get(i).c() - dc);
                            while (matrix.isValidLocation(l)) {
                                result.add(l);
                                ++inc;
                                l = new Matrix2D.Location(locations.get(i).r() - (dr * inc), locations.get(i).c() - (dc * inc));
                            }
                        }
                        {
                            int dr =  locations.get(i).r() - locations.get(j).r();
                            int dc =  locations.get(i).c() - locations.get(j).c();
                            int inc = 1;
                            Matrix2D.Location l = new Matrix2D.Location(locations.get(i).r() - dr, locations.get(i).c() - dc);
                            while (matrix.isValidLocation(l)) {
                                result.add(l);
                                ++inc;
                                l = new Matrix2D.Location(locations.get(i).r() - (dr * inc), locations.get(i).c() - (dc * inc));
                            }
                        }
                    }
                }
            }
        }
        /*for (Matrix2D.Location l : result) {
            matrix.put('X', l);
        }
        System.out.println(matrix);*/
        return result.size();
    }

}
