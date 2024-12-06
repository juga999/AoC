package aoc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        int r = loc.r();
        int c = loc.c();
        Map<Direction, char[]> charsMap = new HashMap<>();
        for (Direction dir : Direction.values()) {
            charsMap.put(dir, getCharArray(radius));
        }

        for (int i = 0; i < radius; i++) {
            for (Direction dir : Direction.values()) {
                charsMap.get(dir)[i] = charAt(r + (i * dir.getDy()), c + (i * dir.getDx()));
            }
        }

        return charsMap.values().stream().toList();
    }
}
