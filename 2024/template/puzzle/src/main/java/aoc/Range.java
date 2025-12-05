package aoc;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class Range {
    long start;
    long end;

    public static List<Range> merge(Range r1, Range r2) {
        if (r1.getEnd() < r2.getStart() || r1.getStart() > r2.getEnd()) {
            // R1 xxxxxxxxxx
            // R2              ooooooo
            // =  ----------   -------
            // R1          xxxxxxxxxx
            // R2 ooooooo
            // =  -------  ----------
            return List.of(r1, r2);
        } else {
            // R1 xxxxxxxxxx
            // R2        ooooooo
            // =  --------------
            long[] values = {
                    r1.getStart(), r1.getEnd(), r2.getStart(), r2.getEnd()
            };
            Arrays.sort(values);
            return List.of(new Range(values[0], values[3]));
        }
    }

    public Range(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public Range(String str) {
        String[] values = str.split("-");
        this.start = Long.parseLong(values[0]);
        this.end = Long.parseLong(values[1]);
    }

    public boolean contains(long value) {
        return value >= start && value <= end;
    }

    public long size() {
        return end - start + 1;
    }

    public boolean equals(Object other) {
        if (other instanceof Range r) {
            return start == r.start && end == r.end;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "%d - %d".formatted(start, end);
    }

}
