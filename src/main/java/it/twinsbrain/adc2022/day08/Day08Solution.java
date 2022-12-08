package it.twinsbrain.adc2022.day08;

import java.net.URISyntaxException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static it.twinsbrain.adc2022.FilesModule.read;
import static it.twinsbrain.adc2022.FilesModule.resource;

public class Day08Solution {

    public static void main(String[] args) throws URISyntaxException {
        var input = read(resource("/day08/input.txt"));
        System.out.printf("Part 1: %s", howManyTreesAreVisible(input));
        System.out.println();
    }

    public static int howManyTreesAreVisible(List<String> input) {
        int[][] grid = parse(input);
        int size = input.size();
        int result = 0;

        for (int i = 0; i < grid.length; i++) {
            var row = grid[i];
            for (int j = 0; j < row.length; j++) {
                var candidate = grid[i][j];
                int finalI = i;
                int finalJ = j;
                boolean visibleFromLeft =
                        isVisibleInRange(IntStream.range(0, j), candidate, (k) -> grid[finalI][k]);
                boolean visibleFromRight =
                        isVisibleInRange(IntStream.range(j + 1, size), candidate, (k) -> grid[finalI][k]);
                boolean visibleFromUp =
                        isVisibleInRange(IntStream.range(0, i), candidate, (k) -> grid[k][finalJ]);
                boolean visibleFromDown =
                        isVisibleInRange(IntStream.range(i + 1, size), candidate, (k) -> grid[k][finalJ]);
                if (visibleFromDown || visibleFromUp || visibleFromLeft || visibleFromRight) {
                    result++;
                }
            }
        }
        return result;
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
