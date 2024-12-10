package aoc.twentyfour.day4;

import aoc.CharMatrix2D;
import aoc.Day;
import aoc.Matrix2D;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day4 {

    static final String XMAS = "XMAS";

    CharMatrix2D matrix;

    public long getWordCountPart1(Matrix2D.Location loc) {
        if (matrix.charAt(loc) != 'X') {
            return 0;
        }
        return matrix.getWithinRadius(loc, XMAS.length()).stream()
                .map(String::copyValueOf)
                .filter(XMAS::equals)
                .count();
    }

    public boolean hasXMas(Matrix2D.Location loc) {
        return hasXMas(loc.r(), loc.c());
    }

    public boolean hasXMas(int r, int c) {
        if (matrix.charAt(r, c) != 'A') {
            return false;
        }

        Set<Character> chars1 = new HashSet<>();
        chars1.add(matrix.charAt(r - 1,c - 1));
        chars1.add(matrix.charAt(r + 1,c + 1));

        Set<Character> chars2 = new HashSet<>();
        chars2.add(matrix.charAt(r - 1,c + 1));
        chars2.add(matrix.charAt(r + 1,c - 1));

        return chars1.containsAll(List.of('M', 'S')) && chars2.containsAll(List.of('M', 'S'));
    }

    public void fillData(String dataPath) {
        List<String> lines = Day.getInputDataLines("2024", getClass(), dataPath);
        matrix = new CharMatrix2D(lines);
    }

    public Long run1() {
        return matrix.locations()
                .mapToLong(this::getWordCountPart1)
                .sum();
    }

    public Long run2() {
        return matrix.locations()
                .filter(this::hasXMas)
                .count();
    }

}
