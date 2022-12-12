package it.twinsbrain.adc2022.day12;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day12Solution {

    public static int part1(List<String> input) {
//        Grid grid = parse(input);
        throw new UnsupportedOperationException();
    }

    static class Grid {
        public char[][] grid;
        private int startX = -1;
        private int startY = -1;

        private int endX = -1;
        private int endY = -1;

        public Grid(int numberOfRows) {
            grid = new char[numberOfRows][];
        }

        public Grid(char[][] grid, int startX, int startY, int endX, int endY) {
            this.grid = grid;
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
        }

        public void acceptRow(int rowNumber, String line) {
            char[] row = new char[line.length()];
            String[] split = line.split("");
            for (int i = 0; i < split.length; i++) {
                String s = split[i];
                if (s.equals("S")) {
                    startX = rowNumber;
                    startY = i;
                }
                if (s.equals("E")) {
                    endX = rowNumber;
                    endY = i;
                }
                row[i] = s.toCharArray()[0];
            }
            grid[rowNumber] = row;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Grid grid1 = (Grid) o;
            return startX == grid1.startX
                    && startY == grid1.startY
                    && endX == grid1.endX
                    && endY == grid1.endY
                    && Arrays.deepEquals(grid, grid1.grid);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(startX, startY, endX, endY);
            result = 31 * result + Arrays.deepHashCode(grid);
            return result;
        }

        @Override
        public String toString() {
            return "Grid{" +
                    "grid=" + Arrays.stream(grid).map(Arrays::toString).collect(Collectors.joining("")) +
                    ", startX=" + startX +
                    ", startY=" + startY +
                    ", endX=" + endX +
                    ", endY=" + endY +
                    '}';
        }
    }

    public static Grid parse(List<String> input) {
        var rows = input.size();
        var grid = new Grid(rows);
        for (int j = 0; j < input.size(); j++) {
            String line = input.get(j);
            grid.acceptRow(j, line);
        }
        return grid;
    }
}
