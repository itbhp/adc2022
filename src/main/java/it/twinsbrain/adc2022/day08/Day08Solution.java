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

    public static int highestScenicScore(List<String> input) {
        int[][] grid = parse(input);
        int size = input.size();
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < grid.length; i++) {
            var row = grid[i];
            for (int j = 0; j < row.length; j++) {
                var candidate = grid[i][j];
                var scoreLeft = 0;
                for (int k = j - 1; k >= 0; k--) {
                    scoreLeft++;
                    if (candidate <= grid[i][k]) {
                        break;
                    }
                }

                var scoreRight = 0;
                for (int k = j + 1; k < size; k++) {
                    scoreRight++;
                    if (candidate <= grid[i][k]) {
                        break;
                    }
                }

                var scoreUp = 0;
                for (int k = i - 1; k >= 0; k--) {
                    scoreUp++;
                    if (candidate <= grid[k][j]) {
                        break;
                    }
                }

                var scoreDown = 0;
                for (int k = i + 1; k < size; k++) {
                    scoreDown++;
                    if (candidate <= grid[k][j]) {
                        break;
                    }
                }
                var scenicScore = scoreDown * scoreUp * scoreLeft * scoreRight;
                if (scenicScore > max) {
                    max = scenicScore;
                }
            }
        }
        return max;
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

    private static boolean isVisibleInRange(IntStream range, int candidate, Function<Integer, Integer> reader) {
        return range.noneMatch(k -> isShorter(candidate, reader.apply(k)));
    }

    private static boolean isShorter(int candidate, int i1) {
        return candidate <= i1;
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
