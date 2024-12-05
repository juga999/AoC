package aoc;

import java.lang.reflect.Array;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class Matrix2D<T> {
    public record Location (int r, int c) {}

    protected final int width;
    protected final int height;

    protected final T[][] data;

    public Matrix2D(Class<T> klass, int width, int height) {
        this.width = width;
        this.height = height;
        data = (T[][]) Array.newInstance(klass, width, height);
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

    public T get(Location loc) {
        return data[loc.r()][loc.c()];
    }

    public T get(int r, int c) {
        return data[r][c];
    }

    public void put(T value, Location loc) {
        put(value, loc.r(), loc.c());
    }

    public void put(T value, int r, int c) {
        data[r][c] = value;
    }

    public void fill(T value) {
        locations().forEach(l -> put(value, l));
    }

    public Stream<Location> locations() {
        return IntStream.range(0, height)
                .boxed()
                .flatMap(row -> IntStream.range(0, width)
                        .mapToObj(col -> new Location(row, col)));
    }
}
