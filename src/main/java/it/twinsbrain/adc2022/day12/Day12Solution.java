package it.twinsbrain.adc2022.day12;

import java.util.*;
import java.util.stream.IntStream;

public class Day12Solution {

  public static int part1(List<String> input) {
    return parse(input).howManyStepsToGetSignal();
  }

  public static int part2(List<String> input) {
    return parse(input).shortestPathToBestSignal();
  }

  static class Grid {
    public final Node[][] grid;
    public Node start;
    public Node end;

    public Grid(int numberOfRows) {
      grid = new Node[numberOfRows][];
    }

    public void acceptRow(int rowNumber, String line) {
      var row = new Node[line.length()];
      var split = line.split("");
      for (int i = 0; i < split.length; i++) {
        var s = split[i];
        row[i] = makeNode(s.charAt(0), rowNumber, i);
      }
      grid[rowNumber] = row;
    }

    public int howManyStepsToGetSignal() {
      return calculateShortestPathFromSource(start).orElse(0);
    }

    private Node makeNode(char elevation, int i, int j) {
      return switch (elevation) {
        case 'S' -> {
          start = Node.start(i, j);
          yield start;
        }
        case 'E' -> {
          end = Node.end(i, j);
          yield end;
        }
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

    public int shortestPathToBestSignal() {
      return Arrays.stream(grid)
          .flatMap(Arrays::stream)
          .filter(it -> it.elevation == 'a')
          .map(Day12Solution::calculateShortestPathFromSource)
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToInt(Integer::intValue)
          .min()
          .orElse(0);
    }

    public void fillNeighbours() {
      for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[i].length; j++) {
          var node = grid[i][j];
          addNeighbours(node, i, j);
        }
      }
    }
  }

  public static Optional<Integer> calculateShortestPathFromSource(Node source) {
    var visited = new HashSet<Node>();
    var queue = new LinkedList<WeightedNode>();

    queue.add(new WeightedNode(source, 0));

    while (!queue.isEmpty()) {
      var current = queue.poll();

      if (current.point.isEnd) return Optional.of(current.totalDistance);

      if (!visited.contains(current.point)) {
        visited.add(current.point);
        current
            .point
            .getAdjacentNodes()
            .forEach(adj -> queue.offer(new WeightedNode(adj, current.totalDistance + 1)));
      }
    }
    return Optional.empty();
  }

  static class WeightedNode implements Comparable<WeightedNode> {

    private final Node point;
    private final Integer totalDistance;

    WeightedNode(Node point, Integer totalDistance) {
      this.point = point;
      this.totalDistance = totalDistance;
    }

    @Override
    public int compareTo(WeightedNode o) {
      return this.totalDistance - o.totalDistance;
    }
  }

  static class Node {

    private final char elevation;

    @SuppressWarnings("unused")
    private final int x;

    @SuppressWarnings("unused")
    private final int y;

    private final boolean isEnd;

    private final List<Node> adjacentNodes = new ArrayList<>();

    public Node(char elevation, int x, int y, boolean isEnd) {
      this.elevation = elevation;
      this.x = x;
      this.y = y;
      this.isEnd = isEnd;
    }

    static Node start(int x, int y) {
      return new Node('a', x, y, false);
    }

    static Node end(int x, int y) {
      return new Node('z', x, y, true);
    }

    public static Node make(char elevation, int i, int j) {
      return new Node(elevation, i, j, false);
    }

    @Override
    public String toString() {
      return String.valueOf(elevation);
    }

    public List<Node> getAdjacentNodes() {
      return adjacentNodes;
    }

    public void addAdjacentNode(Node destination) {
      if (isGoodCandidate(destination)) {
        adjacentNodes.add(destination);
      }
    }

    private boolean isGoodCandidate(Node target) {
      int gap = target.elevation - elevation;
      return gap <= 1;
    }
  }

  public static Grid parse(List<String> input) {
    var rows = input.size();
    var grid = new Grid(rows);
    for (int j = 0; j < input.size(); j++) {
      var line = input.get(j);
      grid.acceptRow(j, line);
    }
    grid.fillNeighbours();
    return grid;
  }
}
