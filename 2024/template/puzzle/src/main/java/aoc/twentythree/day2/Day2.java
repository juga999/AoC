package aoc.twentythree.day2;

import aoc.Day;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day2 {

    public static final class Reveal {
        Map<String, Integer> colors = new HashMap<>();

        public int getCount(String color) {
            return colors.getOrDefault(color, 0);
        }

        public boolean isPossible(int maxReds, int maxGreens, int maxBlues) {
            return getCount("red") <= maxReds
                    && getCount("green") <= maxGreens
                    && getCount("blue") <= maxBlues;
        }
    }

    public static final class Game {
        int id = 0;
        List<Reveal> reveals = new ArrayList<>();

        public int getId() {
            return id;
        }

        public boolean isPossible(int maxReds, int maxGreens, int maxBlues) {
            return reveals.stream().allMatch(r -> r.isPossible(maxReds, maxGreens, maxBlues));
        }

        public int getMax(String color) {
            return reveals.stream().mapToInt(r -> r.getCount(color)).max().orElse(0);
        }

        public int getPower() {
            return getMax("red") * getMax("green") * getMax("blue");
        }
    }

    Reveal getReveal(String[] rawReveal) {
        Reveal reveal = new Reveal();
        for (String items : rawReveal) {
            String[] rawCubes = items.split(",");
            for (String c : rawCubes) {
                String[] rawHand = c.trim().split(" ");
                reveal.colors.put(rawHand[1], Integer.parseInt(rawHand[0]));
            }
        }
        return reveal;
    }

    List<Game> getGames(String dataPath) {
        List<Game> games = new ArrayList<>();
        List<String> lines = Day.getInputDataLines("2023", "day2", dataPath);
        for (String l : lines) {
            Game game = new Game();
            String[] data = l.split(":");
            game.id = Integer.parseInt(data[0].split(" ")[1].trim());
            String[] rawSubsets = data[1].split(";");
            for (String rawSubset : rawSubsets) {
                String[] rawReveal = rawSubset.split(",");
                Reveal reveal = getReveal(rawReveal);
                game.reveals.add(reveal);
            }
            games.add(game);
        }
        return games;
    }

    public Integer run1(String dataPath) {
        List<Game> games = getGames(dataPath);
        return games.stream()
                .filter(g -> g.isPossible(12, 13, 14))
                .map(Game::getId).reduce(Integer::sum).orElse(0);
    }

    public Integer run2(String dataPath) {
        List<Game> games = getGames(dataPath);
        return games.stream().map(Game::getPower).reduce(Integer::sum).orElse(0);
    }
}
