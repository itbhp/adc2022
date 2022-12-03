package it.twinsbrain.adc2022.day03;

import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static it.twinsbrain.adc2022.FilesModule.read;
import static it.twinsbrain.adc2022.FilesModule.resource;
import static java.util.stream.Collectors.toSet;

public class Day03Solution {

    public static void main(String[] args) throws URISyntaxException {
        var input = read(resource("/day03/input.txt"));
        System.out.printf("Priorities sum part 1: %d", part1PrioritiesSum(input));
        System.out.println();
        System.out.printf("Priorities sum part 2: %d", part2PrioritiesSum(input));
    }

    private final static Map<String, Integer> weights = new HashMap<>() {{
        put("a", 1);
        put("b", 2);
        put("c", 3);
        put("d", 4);
        put("e", 5);
        put("f", 6);
        put("g", 7);
        put("h", 8);
        put("i", 9);
        put("j", 10);
        put("k", 11);
        put("l", 12);
        put("m", 13);
        put("n", 14);
        put("o", 15);
        put("p", 16);
        put("q", 17);
        put("r", 18);
        put("s", 19);
        put("t", 20);
        put("u", 21);
        put("v", 22);
        put("w", 23);
        put("x", 24);
        put("y", 25);
        put("z", 26);
        put("A", 27);
        put("B", 28);
        put("C", 29);
        put("D", 30);
        put("E", 31);
        put("F", 32);
        put("G", 33);
        put("H", 34);
        put("I", 35);
        put("J", 36);
        put("K", 37);
        put("L", 38);
        put("M", 39);
        put("N", 40);
        put("O", 41);
        put("P", 42);
        put("Q", 43);
        put("R", 44);
        put("S", 45);
        put("T", 46);
        put("U", 47);
        put("V", 48);
        put("W", 49);
        put("X", 50);
        put("Y", 51);
        put("Z", 52);
    }};

    static int part1PrioritiesSum(List<String> input) {
        return input.stream()
                .map(Day03Solution::representativeChar)
                .map(Day03Solution::toScore)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static int toScore(String key) {
        return weights.get(key);
    }

    private static String representativeChar(String s) {
        var length = s.length();
        var left = s.substring(0, length / 2);
        var right = s.substring(length / 2, length);

        var leftChars = characterSet(left);
        for (char c : right.toCharArray()) {
            if (leftChars.contains(c)) {
                return String.valueOf(c);
            }
        }
        throw new IllegalStateException("Unexpected string format " + s);
    }

    private static Set<Character> characterSet(String left) {
        return left.chars().mapToObj(i -> (char) i).collect(toSet());
    }

    static int part2PrioritiesSum(List<String> input) {
        return chunked(input, 3).stream()
                .map(Day03Solution::representativeChar)
                .map(Day03Solution::toScore)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static String representativeChar(List<String> elvesItems) {
        List<Set<Character>> chars = elvesItems.stream().map(Day03Solution::characterSet).toList();
        var firstElfItems = chars.get(0);
        var secondElfItems = chars.get(1);
        var thirdElfItems = chars.get(2);
        for (Character c : thirdElfItems) {
            if (firstElfItems.contains(c) && secondElfItems.contains(c)) {
                return String.valueOf(c);
            }
        }
        throw new IllegalStateException("Unexpected string format ");
    }

    public static <T> Collection<List<T>> chunked(List<T> inputList, int chunkSize) {
        AtomicInteger counter = new AtomicInteger();
        return inputList.stream().collect(Collectors.groupingBy(it -> counter.getAndIncrement() / chunkSize)).values();
    }
}
