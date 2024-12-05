package aoc.twentyfour.day5;

import aoc.Day;
import aoc.LongMatrix2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Day5 {

    LongMatrix2D matrix;

    List<List<Integer>> sequences = new ArrayList<>();

    public void fillData(String dataPath, int max) {
        matrix = new LongMatrix2D(max, max);
        matrix.fill(0L);
        Iterator<String> lineIterator = Day.getInputDataStream("2024", getClass(), dataPath).iterator();
        while (lineIterator.hasNext()) {
            String line = lineIterator.next();
            if (line.isEmpty()) {
                break;
            }
            List<Integer> values = Arrays.stream(line.split("\\|")).map(Integer::parseInt).toList();
            matrix.put(1L, values.get(0), values.get(1));
            matrix.put(-1L, values.get(1), values.get(0));
        }

        while (lineIterator.hasNext()) {
            String line = lineIterator.next();
            List<Integer> sequence = Arrays.stream(line.split(",")).map(Integer::parseInt).toList();
            sequences.add(sequence);
        }
    }

    public boolean isValid(List<Integer> sequence) {
        Set<Long> result = new HashSet<>();
        for (int i = 0; i < sequence.size() - 1; i++) {
            for (int j = i + 1; j < sequence.size(); j++) {
                result.add(matrix.get(sequence.get(i), sequence.get(j)));
            }
        }
        return !result.contains(-1L);
    }

    public void swap(List<Integer> sequence, int pos1, int pos2) {
        Integer tmp = sequence.get(pos1);
        sequence.set(pos1, sequence.get(pos2));
        sequence.set(pos2, tmp);
    }

    public List<Integer> getValidSequence(List<Integer> sequence) {
        List<Integer> result = new ArrayList<>(sequence);
        do {
            for (int i = 0; i < result.size() - 1; i++) {
                for (int j = i + 1; j < result.size(); j++) {
                    if (matrix.get(result.get(i), result.get(j)) == -1) {
                        swap(result, i, j);
                    }
                }
            }
        } while (!isValid(result));

        return result;
    }

    public Integer getMiddleValue(List<Integer> sequence) {
        return sequence.get(sequence.size() / 2);
    }

    public Integer run1() {
        return sequences.stream()
                .filter(this::isValid)
                .mapToInt(this::getMiddleValue)
                .sum();
    }

    public Integer run2() {
        return sequences.stream()
                .filter(s -> !isValid(s))
                .map(this::getValidSequence)
                .mapToInt(this::getMiddleValue)
                .sum();
    }
}
