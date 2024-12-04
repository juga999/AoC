package aoc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CharMatrix2D {

    public record Location (int r, int c) {}

    protected final int width;
    protected final int height;
    protected final char[][] data;

    public CharMatrix2D(List<String> lines) {
        width = lines.getFirst().length();
        height = lines.size();
        data = new char[height][width];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                data[r][c] = lines.get(r).charAt(c);
            }
        }
    }

    static char[] getCharArray(int size) {
        char[] array = new char[size];
        Arrays.fill(array, '\0');
        return array;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                builder.append(data[r][c]).append(' ');
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    public char charAt(Location loc) {
        return charAt(loc.r(), loc.c());
    }

    public char charAt(int r, int c) {
        if (r >= 0 && r < height && c >= 0 && c < width) {
            return data[r][c];
        } else {
            return '\0';
        }
    }

    public Stream<Location> locations() {
        return IntStream.range(0, height)
                .boxed()
                .flatMap(row -> IntStream.range(0, width)
                        .mapToObj(col -> new Location(row, col)));
    }

    public List<char[]> getInRadius(Location loc, int radius) {
        return getInRadius(loc.r(), loc.c(), radius);
    }

    public List<char[]> getInRadius(int r, int c, int radius) {
        char[] lettersUR = getCharArray(radius);
        char[] lettersR = getCharArray(radius);
        char[] lettersDR = getCharArray(radius);
        char[] lettersUL = getCharArray(radius);
        char[] lettersL = getCharArray(radius);
        char[] lettersDL = getCharArray(radius);
        char[] lettersD = getCharArray(radius);
        char[] lettersU = getCharArray(radius);

        for (int i = 0; i < radius; i++) {
            lettersR[i] = charAt(r, c + i);
            lettersUR[i] = charAt(r - i, c + i);
            lettersDR[i] = charAt(r + i, c + i);
            lettersL[i] = charAt(r, c - i);
            lettersUL[i] = charAt(r - i, c - i);
            lettersDL[i] = charAt(r + i, c - i);
            lettersD[i] = charAt(r + i, c);
            lettersU[i] = charAt(r - i, c);
        }

        return List.of(
                lettersUR, lettersR, lettersDR,
                lettersUL, lettersL, lettersDL,
                lettersD, lettersU);
    }
}
