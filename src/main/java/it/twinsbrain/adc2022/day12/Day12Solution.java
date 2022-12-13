package it.twinsbrain.adc2022.day12;

import java.util.*;
import java.util.stream.Collectors;
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
            calculateShortestPathFromSource(graph.startNode());
            return graph.endNode().distance;
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

            IntStream.rangeClosed(Math.max(i-1, 0), Math.min(i+1, rows-1))
                .filter(it -> it != i)
                .forEach(x -> node.addAdjacentNode(grid[x][j], 1));

            IntStream.rangeClosed(Math.max(j-1, 0), Math.min(j+1, columns-1))
                .filter(it -> it != j)
                .forEach(y -> node.addAdjacentNode(grid[i][y], 1));
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
                    Integer sourceDistance = currentNode.getDistance();
                    if (sourceDistance + edgeWeight < adjacentNode.getDistance()) {
                        adjacentNode.setDistance(sourceDistance + edgeWeight);
                    }
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

    static class Graph {

        private final Set<Node> nodes = new HashSet<>();

        public void addNode(Node nodeA) {
            nodes.add(nodeA);
        }

        public Node startNode() {
            return nodes.stream().filter(it -> it.isStart).findFirst().get();
        }

        public Node endNode() {
            return nodes.stream().filter(it -> it.isEnd).findFirst().get();
        }
    }

    static class Node {

        private final char elevation;
        private final int x;
        private final int y;
        private final boolean isStart;
        private final boolean isEnd;

        private Integer distance = Integer.MAX_VALUE;

        Map<Node, Integer> adjacentNodes = new HashMap<>();

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

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        public Map<Node, Integer> getAdjacentNodes() {
            return adjacentNodes;
        }

        public void addAdjacentNode(Node destination, int distance) {
            if (sameHeightOrOneShorter(destination)) {
                adjacentNodes.put(destination, distance);
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
