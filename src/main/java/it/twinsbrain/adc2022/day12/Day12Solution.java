package it.twinsbrain.adc2022.day12;

import java.util.*;
import java.util.stream.IntStream;

public class Day12Solution {

    public static int part1(List<String> input) {
        Grid grid = parse(input);
        return grid.howManyStepsToGetSignal();
    }

    static class Grid {
        public Node[][] grid;

        public Grid(int numberOfRows) {
            grid = new Node[numberOfRows][];
        }

        public void acceptRow(int rowNumber, String line) {
            Node[] row = new Node[line.length()];
            String[] split = line.split("");
            for (int i = 0; i < split.length; i++) {
                String s = split[i];
                row[i] = makeNode(s.charAt(0), rowNumber, i);
            }
            grid[rowNumber] = row;
        }


        public int howManyStepsToGetSignal() {
            var graph = new Graph();
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    Node node = grid[i][j];
                    addNeighbours(node, i, j);
                    graph.addNode(node);
                }
            }
            return calculateShortestPathFromSource(graph.startNode());
        }

        private Node makeNode(char elevation, int i, int j) {
            return switch (elevation) {
                case 'S' -> Node.start(i, j);
                case 'E' -> Node.end(i, j);
                default -> Node.make(elevation, i, j);
            };
        }

        private void addNeighbours(Node node, int i, int j) {
            var rows = grid.length;
            var columns = grid[0].length;

            IntStream.rangeClosed(Math.max(i - 1, 0), Math.min(i + 1, rows - 1))
                    .filter(it -> it != i)
                    .forEach(x -> node.addAdjacentNode(grid[x][j]));

            IntStream.rangeClosed(Math.max(j - 1, 0), Math.min(j + 1, columns - 1))
                    .filter(it -> it != j)
                    .forEach(y -> node.addAdjacentNode(grid[i][y]));
        }
    }

    public static int calculateShortestPathFromSource(Node source) {
        Set<Node> visited = new HashSet<>();
        ArrayDeque<Node> queue = new ArrayDeque<>();
        ArrayDeque<Integer> steps = new ArrayDeque<>();

        queue.add(source);
        steps.add(0);

        while (!queue.isEmpty()) {
            Node current = queue.removeFirst();
            visited.add(current);

            int currentSteps = steps.removeFirst();
            if (current.isEnd) return currentSteps;

            current.getAdjacentNodes().forEach(adj -> {
                if (!visited.contains(adj)) {
                    queue.addLast(adj);
                    steps.addLast(currentSteps + 1);
                }
            });
        }
        return -1;
    }

    static class Graph {

        private final Set<Node> nodes = new HashSet<>();

        public void addNode(Node nodeA) {
            nodes.add(nodeA);
        }

        public Node startNode() {
            return nodes.stream().filter(it -> it.isStart).findFirst().orElseThrow();
        }
    }

    static class Node {

        private final char elevation;

        @SuppressWarnings("unused")
        private final int x;

        @SuppressWarnings("unused")
        private final int y;

        private final boolean isStart;

        private final boolean isEnd;

        private final List<Node> adjacentNodes = new ArrayList<>();

        public Node(char elevation, int x, int y, boolean isStart, boolean isEnd) {
            this.elevation = elevation;
            this.x = x;
            this.y = y;
            this.isStart = isStart;
            this.isEnd = isEnd;
        }

        static Node start(int x, int y) {
            return new Node('a', x, y, true, false);
        }

        static Node end(int x, int y) {
            return new Node('z', x, y, false, true);
        }

        public static Node make(char elevation, int i, int j) {
            return new Node(elevation, i, j, false, false);
        }

        @Override
        public String toString() {
            return "" + elevation;
        }

        public List<Node> getAdjacentNodes() {
            return adjacentNodes;
        }

        public void addAdjacentNode(Node destination) {
            if (sameHeightOrOneShorter(destination)) {
                adjacentNodes.add(destination);
            }
        }

        private boolean sameHeightOrOneShorter(Node target) {
            int gap = ((int) target.elevation) - ((int) elevation);
            return gap == 1 || gap == 0;
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
