package it.twinsbrain.adc2022.day12;

import java.util.*;
import java.util.stream.Collectors;

public class Day12Solution {

    public static int part1(List<String> input) {
        Grid grid = parse(input);
        return grid.howManyStepsToGetSignal();
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

        public int howManyStepsToGetSignal() {
            var graph = new Graph();
            var nodeGrid = new Node[grid.length][];
            Node startNode = null;
            Node endNode = null;
            for (int i = 0; i < nodeGrid.length; i++) {
                var columns = grid[i].length;
                var row = new Node[columns];
                for (int j = 0; j < columns; j++) {
                    Node node = makeNode(i, j);
                    if (i == startX && j == startY) {
                        startNode = node;
                    }
                    if (i == endX && j == endY) {
                        endNode = node;
                    }
                    row[j] = node;
                }
                nodeGrid[i] = row;
            }
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    Node node = nodeGrid[i][j];
                    addNeighbours(node, nodeGrid, i, j);
                    graph.addNode(node);
                }
            }
            calculateShortestPathFromSource(Objects.requireNonNull(startNode));
            return Objects.requireNonNull(endNode).distance;
        }

        private Node makeNode(int i, int j) {
            char elevation = grid[i][j];
            return switch (elevation) {
                case 'S' -> new Node('a', i, j);
                case 'E' -> new Node('z', i, j);
                default -> new Node(elevation, i, j);
            };
        }

        private void addNeighbours(Node node, Node[][] grid, int i, int j) {
            var rows = grid.length;
            var columns = grid[0].length;
            if (i - 1 >= 0 && i - 1 < rows) {
                Node neighbour = grid[i - 1][j];
                if (comesAfter(node.elevation, neighbour)) {
                    node.addDestination(neighbour, 1);
                }
            }
            if (i + 1 >= 0 && i + 1 < rows) {
                Node neighbour = grid[i + 1][j];
                if (comesAfter(node.elevation, neighbour)) {
                    node.addDestination(neighbour, 1);
                }
            }
            if (j - 1 >= 0 && j - 1 < columns) {
                Node neighbour = grid[i][j - 1];
                if (comesAfter(node.elevation, neighbour)) {
                    node.addDestination(neighbour, 1);
                }
            }
            if (j + 1 >= 0 && j + 1 < columns) {
                Node neighbour = grid[i][j + 1];
                if (comesAfter(node.elevation, neighbour)) {
                    node.addDestination(neighbour, 1);
                }
            }
        }

        private boolean comesAfter(char id, Node c) {
            int distance = ((int) c.elevation) - ((int) id);
            return distance == 1 || distance == 0;
        }
    }

    public static void calculateShortestPathFromSource(Node source) {
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
    }

    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void calculateMinimumDistance(
            Node evaluationNode,
            Integer edgeWeigh,
            Node sourceNode
    ) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    static class Graph {

        private final Set<Node> nodes = new HashSet<>();

        public void addNode(Node nodeA) {
            nodes.add(nodeA);
        }
    }

    static class Node {

        private final char elevation;
        private final int x;
        private final int y;

        private List<Node> shortestPath = new LinkedList<>();

        private Integer distance = Integer.MAX_VALUE;

        Map<Node, Integer> adjacentNodes = new HashMap<>();

        public Node(char elevation, int x, int y) {
            this.elevation = elevation;
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "" + elevation;
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        public List<Node> getShortestPath() {
            return shortestPath;
        }

        public void setShortestPath(List<Node> shortestPath) {
            this.shortestPath = shortestPath;
        }

        public Map<Node, Integer> getAdjacentNodes() {
            return adjacentNodes;
        }

        public void addDestination(Node destination, int distance) {
            adjacentNodes.put(destination, distance);
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
