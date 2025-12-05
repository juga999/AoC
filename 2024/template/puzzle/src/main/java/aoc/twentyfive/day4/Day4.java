package aoc.twentyfive.day4;

import aoc.CharMatrix2D;
import aoc.Day;
import aoc.Matrix2D;

import java.util.List;

public class Day4 {

    boolean canBeReached(CharMatrix2D world, Matrix2D.Location l) {
        return world.getWithinRadius(l, 1)
                .stream()
                .filter(c -> c[0] == '@')
                .count() < 4;
    }

    public long run1(String dataPath) {
        List<String> data = Day.getInputDataLines("2025", getClass(), dataPath);
        CharMatrix2D world = new CharMatrix2D(data);

        return world.locations((c) -> c == '@')
                .filter(l -> canBeReached(world, l))
                .count();
    }

    public long run2(String dataPath) {
        List<String> data = Day.getInputDataLines("2025", getClass(), dataPath);
        CharMatrix2D world = new CharMatrix2D(data);

        long removedCount = 0;
        List<Matrix2D.Location> toRemoveList;
        do {
            toRemoveList = world.locations((c) -> c == '@')
                    .filter(l -> canBeReached(world, l))
                    .toList();
            for (Matrix2D.Location l : toRemoveList) {
                world.put('.', l);
            }
            removedCount += toRemoveList.size();
        } while (!toRemoveList.isEmpty());

        return removedCount;
    }
}
