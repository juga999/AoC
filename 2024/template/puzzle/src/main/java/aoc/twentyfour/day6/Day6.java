package aoc.twentyfour.day6;

import aoc.CharMatrix2D;
import aoc.Day;
import aoc.Matrix2D;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Day6 {

    @Getter
    @Setter
    public final static class Guard {
        Matrix2D.Location loc;
        Matrix2D.Direction dir;

        public Guard(Matrix2D.Location loc) {
            this.loc = loc;
            this.dir = Matrix2D.Direction.UP;
        }

        public Matrix2D.Location peekNextLocation() {
            return new Matrix2D.Location(loc.r() + dir.getDy(), loc.c() + dir.getDx());
        }

        public void moveTo(Matrix2D.Location nextLocation) {
            setLoc(nextLocation);
        }

        public void changeDirection() {
            Matrix2D.Direction newDir = switch(dir) {
                case Matrix2D.Direction.UP -> Matrix2D.Direction.RIGHT;
                case Matrix2D.Direction.RIGHT -> Matrix2D.Direction.DOWN;
                case Matrix2D.Direction.DOWN -> Matrix2D.Direction.LEFT;
                case Matrix2D.Direction.LEFT -> Matrix2D.Direction.UP;
                default -> null;
            };
            dir = newDir;
        }

        public Pair<Matrix2D.Location, Matrix2D.Direction> getStep() {
            return Pair.of(loc, dir);
        }

    }

    CharMatrix2D matrix;
    Guard guard;
    Matrix2D.Location firstLocation;

    public void fillData(String dataPath) {
        List<String> lines = Day.getInputDataLines("2024", getClass(), dataPath);
        matrix = new CharMatrix2D(lines);
        guard = new Guard(matrix.findFirst('^'));
        firstLocation = guard.getLoc();
        matrix.put('.', firstLocation);
    }

    public void resetGuard() {
        guard.setLoc(firstLocation);
        guard.setDir(Matrix2D.Direction.UP);
        matrix.put('.', firstLocation);
    }

    public Pair<Matrix2D.Location, Matrix2D.Direction> moveGuard() {
        Matrix2D.Location nextLocation = guard.peekNextLocation();
        char item = matrix.charAt(nextLocation);
        if (item == '\0') {
            return null;
        }
        if (item == '.') {
            guard.moveTo(nextLocation);
        } else {
            guard.changeDirection();
        }
        return guard.getStep();
    }

    public int run1() {
        Set<Matrix2D.Location> locations = new HashSet<>();
        Matrix2D.Location currentLocation = firstLocation;
        while (currentLocation != null) {
            locations.add(currentLocation);
            currentLocation = Optional.ofNullable(moveGuard()).map(Pair::getLeft).orElse(null);
        }
        return locations.size();
    }

    public boolean isPatrolCircle() {
        resetGuard();
        Set<Pair<Matrix2D.Location, Matrix2D.Direction>> patrol = new HashSet<>();
        Pair<Matrix2D.Location, Matrix2D.Direction> currentStep = guard.getStep();
        while (currentStep != null && !patrol.contains(currentStep)) {
            patrol.add(currentStep);
           currentStep = moveGuard();
        }
        return currentStep != null;
    }

    public int run2() {
        int[] count = {0};
        matrix.locations().forEach(l -> {
            if (matrix.charAt(l) == '.') {
                resetGuard();
                matrix.put('#', l);
                if (isPatrolCircle()) {
                    ++count[0];
                }
                matrix.put('.', l);
            }
        });
        return count[0];
    }
}
