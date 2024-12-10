package aoc.twentyfour.day9;

import aoc.Day;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Day9 {

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static final class File {
        final int id;
        List<Integer> blocks = new ArrayList<>();
    }

    int totalBlocks = 0;
    List<File> files = new ArrayList<>();
    char[] fileSystem;

    public Integer getNextAvailableSlot(int from) {
        for (int i = from; i < fileSystem.length; i++) {
            if (fileSystem[i] == '.') {
                return i;
            }
        }
        return null;
    }

    public Integer moveFile(File f, Integer slot) {
        Integer nextSlot = slot;
        for (int i = f.getBlocks().size() - 1; i >= 0; i--) {
            if (nextSlot != null) {
                int old = f.getBlocks().get(i);
                if (old > nextSlot) {
                    f.getBlocks().set(i, nextSlot);
                    fileSystem[old] = '.';
                    fileSystem[nextSlot] = 'X';
                    nextSlot = getNextAvailableSlot(nextSlot + 1);
                }
            } else {
                return null;
            }
        }
        return nextSlot;
    }

    public Integer getSlotForWholeFile(File f) {
        String emptyArea = ".".repeat(f.getBlocks().size());
        String fs = new String(fileSystem);
        int slot = fs.indexOf(emptyArea);
        if (slot >= 0 && slot < f.getBlocks().getFirst()) {
            return slot;
        } else {
            return null;
        }
    }

    public void moveWholeFile(File f) {
        Integer slot = getSlotForWholeFile(f);
        if (slot != null) {
            moveFile(f, slot);
        }
    }

    public void moveFiles() {
        Integer slot = getNextAvailableSlot(0);
        for (File f : files.reversed()) {
            slot = moveFile(f, slot);
        }
    }

    public void moveWholeFiles() {
        for (File f : files.reversed()) {
            moveWholeFile(f);
        }
    }

    public Long getChecksum(File f) {
        return f.getBlocks().stream()
                .mapToLong(p -> (long) p * f.getId())
                .sum();
    }

    public void init(String dataPath) {
        char[] input = Day.getInputDataLines("2024", getClass(), dataPath).getFirst().toCharArray();
        int i = 0;
        while (i < input.length) {
            File f = new File(files.size());
            for (int j = 0; j < Day.toInt(input[i]); j++) {
                f.getBlocks().add(totalBlocks + j);
            }
            files.add(f);
            totalBlocks += f.getBlocks().size();
            ++i;
            if (i < input.length) {
                totalBlocks += Day.toInt(input[i]);
                ++i;
            }
        }
        fileSystem = new char[totalBlocks];
        Arrays.fill(fileSystem, '.');
        for (File f : files) {
            for (int b : f.getBlocks()) {
                fileSystem[b] = 'X';
            }
        }
    }

    public Long run1() {
        moveFiles();
        return files.stream()
                .mapToLong(this::getChecksum)
                .sum();
    }

    public Long run2() {
        moveWholeFiles();
        return files.stream()
                .mapToLong(this::getChecksum)
                .sum();
    }
}
