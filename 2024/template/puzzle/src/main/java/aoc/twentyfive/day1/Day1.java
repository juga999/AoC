package aoc.twentyfive.day1;

import aoc.Day;

import java.util.List;
import java.util.stream.Stream;

public class Day1 {

    static final class Instruction {
        int direction;
        int count;

        Instruction(String str) {
            if (str.charAt(0) == 'L') {
                this.direction = -1;
            } else {
                this.direction = 1;
            }
            this.count = Integer.parseInt(str.substring(1));
        }
    }

    int position = 50;

    Stream<Instruction> getInputStream(String dataPath) {
        return Day.getInputDataStream("2025", getClass(), dataPath)
                .map(Instruction::new);
    }

    long changePosition(Instruction instruction) {
        long zeroCount = 0;
        if (instruction.direction > 0) {
            for (int i = 0; i < instruction.count; i++) {
                position += 1;
                if (position == 100) {
                    position = 0;
                    ++zeroCount;
                }
            }
        } else {
            for (int i = 0; i < instruction.count; i++) {
                position -= 1;
                if (position == 0) {
                    ++zeroCount;
                } else if (position == -1) {
                    position = 99;
                }
            }
        }
        return zeroCount;
    }

    public long run1(String dataPath) {
        long result = 0;
        List<Instruction> instructions = getInputStream(dataPath).toList();
        for (var instruction : instructions) {
            changePosition(instruction);
            if (position == 0) {
                ++result;
            }
        }
        return result;
    }

    public long run2(String dataPath) {
        long result = 0;
        List<Instruction> instructions = getInputStream(dataPath).toList();
        for (var instruction : instructions) {
            result += changePosition(instruction);
        }
        return result;
    }
}
