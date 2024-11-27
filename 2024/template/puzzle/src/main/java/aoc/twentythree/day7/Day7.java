package aoc.twentythree.day7;

import aoc.Day;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day7 {

    public enum Card {
        JOKER,
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN,
        J, Q, K, A
    }

    public enum Type {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREES,
        FULL_HOUSE,
        FOURS,
        FIVES
    }

    public static class Hand implements Comparable<Hand> {
        List<Card> cards = new ArrayList<>();
        long bid = 0;

        public static Type getType(Map<Card, Long> countMap) {
            Collection<Long> counts = countMap.values();
            long jockers = countMap.getOrDefault(Card.JOKER, 0L);
            if (counts.contains(5L)) {
                return Type.FIVES;
            } else if (counts.contains(4L)) {
                if (jockers > 0) {
                    return Type.FIVES;
                } else {
                    return Type.FOURS;
                }
            } else if (counts.contains(3L) && counts.contains(2L)) {
                if (jockers == 3 || jockers == 2) {
                    return Type.FIVES;
                } else if (jockers == 1) {
                    return Type.FOURS;
                } else {
                    return Type.FULL_HOUSE;
                }
            } else if (counts.contains(3L) && !counts.contains(2L)) {
                if (jockers == 3) {
                    return Type.FOURS;
                } else if (jockers == 2) {
                    return Type.FIVES;
                } else if (jockers == 1) {
                    return Type.FOURS;
                } else {
                    return Type.THREES;
                }
            } else if (counts.stream().filter(c -> c == 2L).count() == 2) {
                if (jockers == 2) {
                    return Type.FOURS;
                } else if (jockers == 1) {
                    return Type.FULL_HOUSE;
                } else {
                    return Type.TWO_PAIR;
                }
            } else if (counts.contains(2L)) {
                if (jockers > 0) {
                    return Type.THREES;
                } else {
                    return Type.ONE_PAIR;
                }
            } else {
                if (jockers > 0) {
                    return Type.ONE_PAIR;
                } else {
                    return Type.HIGH_CARD;
                }
            }
        }

        public Type getType() {
            Map<Card, Long> countMap = cards.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            return getType(countMap);
        }

        @Override
        public int compareTo(Hand other) {
            Type type1 = getType();
            Type type2 = other.getType();
            int res = type1.compareTo(type2);
            if (res == 0) {
                for (int i = 0; i < cards.size(); i++) {
                    int res2 = cards.get(i).compareTo(other.cards.get(i));
                    if (res2 != 0) {
                        return res2;
                    }
                }
                return 0;
            } else {
                return res;
            }
        }

        @Override
        public String toString() {
            return getType().name() + ": " + cards.stream().map(Enum::name).collect(Collectors.joining(","));
        }
    }

    public Hand parseHand(String str, long bid, boolean withJoker) {
        Hand hand = new Hand();
        hand.bid = bid;
        for (char c : str.toCharArray()) {
            switch (c) {
                case '2' -> hand.cards.add(Card.TWO);
                case '3' -> hand.cards.add(Card.THREE);
                case '4' -> hand.cards.add(Card.FOUR);
                case '5' -> hand.cards.add(Card.FIVE);
                case '6' -> hand.cards.add(Card.SIX);
                case '7' -> hand.cards.add(Card.SEVEN);
                case '8' -> hand.cards.add(Card.EIGHT);
                case '9' -> hand.cards.add(Card.NINE);
                case 'T' -> hand.cards.add(Card.TEN);
                case 'J' -> {
                    if (withJoker) {
                        hand.cards.add(Card.JOKER);
                    } else {
                        hand.cards.add(Card.J);
                    }
                }
                case 'Q' -> hand.cards.add(Card.Q);
                case 'K' -> hand.cards.add(Card.K);
                case 'A' -> hand.cards.add(Card.A);
            }
        }
        return hand;
    }

    public long run1(String dataPath) {
        List<Hand> hands = new ArrayList<>();
        List<String> lines = Day.getInputDataLines("2023", "day7", dataPath);
        for (String l : lines) {

            String[] data = l.split(" ");
            Hand hand = parseHand(data[0].trim(), Long.parseLong(data[1].trim()), false);
            hands.add(hand);
        }
        long result = 0;
        Collections.sort(hands);
        for (int i = 0; i < hands.size(); i++) {
            result += hands.get(i).bid * (i+1);
        }
        return result;
    }

    public long run2(String dataPath) {
        List<Hand> hands = new ArrayList<>();
        List<String> lines = Day.getInputDataLines("2023", "day7", dataPath);
        for (String l : lines) {

            String[] data = l.split(" ");
            Hand hand = parseHand(data[0].trim(), Long.parseLong(data[1].trim()), true);
            hands.add(hand);
        }
        long result = 0;
        Collections.sort(hands);
        for (int i = 0; i < hands.size(); i++) {
            result += hands.get(i).bid * (i+1);
        }
        return result;
    }
}
