package it.twinsbrain.adc2022.day08;

import java.net.URISyntaxException;
import java.util.List;

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
        int gridSideSize = input.size();
        int result = 0;

        for (int i = 0; i < grid.length; i++) {
            var row = grid[i];
            for (int j = 0; j < row.length; j++) {
                var candidate = grid[i][j];

                boolean visibleFromLeft = true;
                for (int k = j - 1; k >= 0; k--) {
                    if (candidate <= grid[i][k]) {
                        visibleFromLeft = false;
                        break;
                    }
                }

                boolean visibleFromRight = true;
                for (int k = j + 1; k < gridSideSize; k++) {
                    if (candidate <= grid[i][k]) {
                        visibleFromRight = false;
                        break;
                    }
                }

                boolean visibleFromUp = true;
                for (int k = i - 1; k >= 0; k--) {
                    if (candidate <= grid[k][j]) {
                        visibleFromUp = false;
                        break;
                    }
                }

                boolean visibleFromDown = true;
                for (int k = i + 1; k < gridSideSize; k++) {
                    if (candidate <= grid[k][j]) {
                        visibleFromDown = false;
                        break;
                    }
                }
                if (visibleFromDown || visibleFromUp || visibleFromLeft || visibleFromRight) {
                    result++;
                }
            }
        }
        return result;
    }

    private static boolean onEdge(int i, int j, int gridSideSize) {
        return i == 0 || i == gridSideSize - 1 || j == 0 || j == gridSideSize - 1;
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
