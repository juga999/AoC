package aoc.twentyfour.day7;

import aoc.Day;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day7 {

    @Getter
    @Setter
    public static final class Equation {
        private long expectedResult;
        private List<Long> sequence;
        private List<Character> operators = new ArrayList<>();

        public Equation(String str) {
            String[] data = str.split(":");
            expectedResult = Long.parseLong(data[0].trim());
            sequence = Arrays.stream(data[1].trim().split(" ")).map(Long::parseLong).toList();
        }

        void nextStepTwoOperands(Long tmpResult, int depth, Set<Long> results) {
            if (depth == 0) {
                results.add(tmpResult);
                return;
            }
            long number = sequence.get(depth);
            {
                long addRes = tmpResult - number;
                nextStepTwoOperands(addRes, depth - 1, results);
            }
            if (tmpResult % number == 0) {
                long multRes = tmpResult / number;
                nextStepTwoOperands(multRes, depth - 1, results);
            }
        }

        public Long deConcat(long from, long number) {
            String fromStr = "%d".formatted(from);
            String numberStr = "%d".formatted(number);
            if (fromStr.length() > numberStr.length() && fromStr.endsWith(numberStr)) {
                return Long.parseLong(fromStr.substring(0, fromStr.length() - numberStr.length()));
            } else {
                return null;
            }
        }

        void nextStepThreeOperands(Long tmpResult, int depth, Set<Long> results) {
            if (tmpResult <= 0) {
                return;
            }
            if (depth == 0) {
                results.add(tmpResult);
                return;
            }
            long number = sequence.get(depth);
            {
                long addRes = tmpResult - number;
                nextStepThreeOperands(addRes, depth - 1, results);
            }
            if (tmpResult % number == 0) {
                long multRes = tmpResult / number;
                nextStepThreeOperands(multRes, depth - 1, results);
            }
            Long deConcatRes = deConcat(tmpResult, number);
            if (deConcatRes != null) {
                nextStepThreeOperands(deConcatRes, depth - 1, results);
            }

        }

        public boolean isValidTwoOperands() {
            return isValidTwoOperands(sequence);
        }

        public boolean isValidTwoOperands(List<Long> seq) {
            Set<Long> results = new HashSet<>();
            nextStepTwoOperands(expectedResult, seq.size() - 1, results);
            return results.contains(seq.getFirst());
        }

        public boolean isValidThreeOperands() {
            Set<Long> results = new HashSet<>();
            nextStepThreeOperands(expectedResult, sequence.size() - 1, results);
            return results.contains(sequence.getFirst());
        }
    }

    public long run1(String dataPath) {
        return Day.getInputDataStream("2024", getClass(), dataPath)
                .map(Equation::new)
                .filter(Equation::isValidTwoOperands)
                .mapToLong(Equation::getExpectedResult)
                .sum();
    }

    public long run2(String dataPath) {
        return Day.getInputDataStream("2024", getClass(), dataPath)
                .map(Equation::new)
                .filter(Equation::isValidThreeOperands)
                .mapToLong(Equation::getExpectedResult)
                .sum();
    }

}
