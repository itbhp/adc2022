package it.twinsbrain.adc2022.day08;

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.IntStream;

import static it.twinsbrain.adc2022.FilesModule.read;
import static it.twinsbrain.adc2022.FilesModule.resource;

public class Day08Solution {

    public static void main(String[] args) throws URISyntaxException {
        var input = read(resource("/day08/input.txt"));
        System.out.printf("Part 1: %s", howManyTreesAreVisible(input));
        System.out.println();
        System.out.printf("Part 2: %s", highestScenicScore(input));
    }

    public static int howManyTreesAreVisible(List<String> input) {
        int[][] grid = parse(input);
        int size = input.size();
        AtomicInteger result = new AtomicInteger(0);

        IntStream.range(0, size).forEach(i ->
                IntStream.range(0, size).forEach(j -> {
                    var candidate = grid[i][j];
                    boolean visibleFromLeft =
                            isVisibleInRange(IntStream.range(0, j), candidate, (k) -> grid[i][k]);
                    boolean visibleFromRight =
                            isVisibleInRange(IntStream.range(j + 1, size), candidate, (k) -> grid[i][k]);
                    boolean visibleFromUp =
                            isVisibleInRange(IntStream.range(0, i), candidate, (k) -> grid[k][j]);
                    boolean visibleFromDown =
                            isVisibleInRange(IntStream.range(i + 1, size), candidate, (k) -> grid[k][j]);
                    if (visibleFromDown || visibleFromUp || visibleFromLeft || visibleFromRight) {
                        result.incrementAndGet();
                    }
                }));
        return result.get();
    }

    public static int highestScenicScore(List<String> input) {
        int[][] grid = parse(input);
        int size = input.size();
        AtomicInteger max = new AtomicInteger(Integer.MIN_VALUE);

        IntStream.range(0, size).forEach(i ->
                IntStream.range(0, size).forEach(j -> {
                    var candidate = grid[i][j];
                    int scoreLeft = visibleFromLeft(j, candidate, k -> grid[i][k]);
                    int scoreRight = visibleFromRight(size, j, candidate, k -> grid[i][k]);
                    int scoreUp = visibleFromUp(i, candidate, k -> grid[k][j]);
                    int scoreDown = visibleFromDown(size, i, candidate, k -> grid[k][j]);
                    var scenicScore = scoreDown * scoreUp * scoreLeft * scoreRight;
                    if (scenicScore > max.intValue()) {
                        max.set(scenicScore);
                    }
                }));
        return max.intValue();
    }

    private static int visibleFromDown(int size, int i, int candidate, Function<Integer, Integer> reader) {
        return checkHowManyVisibleFromIUpToSize(size, i, candidate, reader);
    }

    private static int visibleFromUp(int i, int candidate, Function<Integer, Integer> reader) {
        return checkHowManyVisibleFromJDownToZero(i, candidate, reader);
    }

    private static int visibleFromRight(int size, int j, int candidate, Function<Integer, Integer> reader) {
        return checkHowManyVisibleFromIUpToSize(size, j, candidate, reader);
    }

    private static int visibleFromLeft(int j, int candidate, Function<Integer, Integer> reader) {
        return checkHowManyVisibleFromJDownToZero(j, candidate, reader);
    }

    private static int checkHowManyVisibleFromIUpToSize(
            int size, int i, int candidate, Function<Integer, Integer> reader
    ) {
        var scoreDown = 0;
        for (int k = i + 1; k < size; k++) {
            scoreDown++;
            if (isShorter(candidate, reader.apply(k))) {
                break;
            }
        }
        return scoreDown;
    }

    private static int checkHowManyVisibleFromJDownToZero(
            int j, int candidate, Function<Integer, Integer> reader
    ) {
        var scoreLeft = 0;
        for (int k = j - 1; k >= 0; k--) {
            scoreLeft++;
            if (isShorter(candidate, reader.apply(k))) {
                break;
            }
        }
        return scoreLeft;
    }

    private static boolean isVisibleInRange(IntStream range, int candidate, Function<Integer, Integer> reader) {
        return range.noneMatch(k -> isShorter(candidate, reader.apply(k)));
    }

    private static boolean isShorter(int candidate, int other) {
        return candidate <= other;
    }

    public static int[][] parse(List<String> input) {
        var size = input.get(0).length();
        var res = new int[size][];
        for (int i = 0; i < res.length; i++) {
            res[i] = new int[size];
        }
        for (int i = 0; i < input.size(); i++) {
            String[] stringNumbers = input.get(i).split("");
            for (int j = 0; j < stringNumbers.length; j++) {
                res[i][j] = Integer.parseInt(stringNumbers[j]);
            }
        }
        return res;
    }
}
