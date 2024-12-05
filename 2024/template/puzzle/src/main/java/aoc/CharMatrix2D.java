package aoc;

import java.util.Arrays;
import java.util.List;

public class CharMatrix2D extends Matrix2D<Character> {

    public CharMatrix2D(List<String> lines) {
        super(Character.class, lines.getFirst().length(), lines.size());
        locations().forEach(l -> {
            put(lines.get(l.r()).charAt(l.c()), l);
        });
    }

    static char[] getCharArray(int size) {
        char[] array = new char[size];
        Arrays.fill(array, '\0');
        return array;
    }

    public Character charAt(Location loc) {
        return charAt(loc.r(), loc.c());
    }

    public Character charAt(int r, int c) {
        if (r >= 0 && r < height && c >= 0 && c < width) {
            return get(r, c);
        } else {
            return '\0';
        }
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
