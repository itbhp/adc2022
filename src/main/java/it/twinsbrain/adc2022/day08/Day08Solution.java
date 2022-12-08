package it.twinsbrain.adc2022.day08;

import java.util.List;

public class Day08Solution {

    public static int howManyTreesAreVisible(List<String> input) {
        int[][] grid = parse(input);
        int gridSideSize = input.size();
        int result = (gridSideSize * 4) - 4;
        for (int i = 1; i < grid.length - 1; i++) {
            var row = grid[i];
            for (int j = 1; j < row.length - 1; j++) {
                var candidate = grid[i][j];

                boolean visibleFromLeft = true;
                for (int k = j - 1; k > 1; k--) {
                    if (candidate < grid[i][k]) {
                        visibleFromLeft = false;
                        break;
                    }
                }

                boolean visibleFromRight = true;
                for (int k = j + 1; k < gridSideSize - 1; k++) {
                    if (candidate < grid[i][k]) {
                        visibleFromRight = false;
                        break;
                    }
                }

                boolean visibleFromUp = true;
                for (int k = i - 1; k > 1; k--) {
                    if (candidate < grid[k][j]) {
                        visibleFromUp = false;
                        break;
                    }
                }

                boolean visibleFromDown = true;
                for (int k = i + 1; k < gridSideSize - 1; k++) {
                    if (candidate < grid[k][j]) {
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
