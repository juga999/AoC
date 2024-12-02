package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Day {

    public static Path getInputDataPath(String year, String day, String name) {
        return Paths.get("src", "test", "resources", year, day, name);
    }

    public static List<String> getInputDataLines(String year, String day, String name) {
        Path filePath = Day.getInputDataPath(year, day, name);
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getInputDataLines(String year, Class<?> day, String name) {
        return getInputDataLines(year, day.getSimpleName().toLowerCase(), name);
    }

    public static <T> void print(Iterable<T> list) {
        String str = StreamSupport.stream(list.spliterator(), false)
                .map(Object::toString).collect(Collectors.joining(","));
        System.out.println(str);
    }

}
