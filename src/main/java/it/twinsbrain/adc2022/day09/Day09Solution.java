package it.twinsbrain.adc2022.day09;

import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static it.twinsbrain.adc2022.FilesModule.read;
import static it.twinsbrain.adc2022.FilesModule.resource;
import static java.lang.Math.abs;

public class Day09Solution {

    public static void main(String[] args) throws URISyntaxException {
        var input = read(resource("/day09/input.txt"));
        System.out.printf("Part 1: %s", part1(input));
        System.out.println();
    }

    public static int part1(List<String> input) {
        List<Command> commands = parseCommands(input);
        var journey = new Journey();
        commands.forEach(journey::accept);
        return journey.howManyTailUniquePositions();
    }

    public static List<Command> parseCommands(List<String> input) {
        return input.stream()
                .map(Day09Solution::toCommand)
                .collect(Collectors.toList());
    }

    private static Command toCommand(String line) {
        String[] parts = line.split(" ");
        return switch (parts[0]) {
            case "R" -> new Right(Integer.parseInt(parts[1]));
            case "L" -> new Left(Integer.parseInt(parts[1]));
            case "U" -> new Up(Integer.parseInt(parts[1]));
            default -> new Down(Integer.parseInt(parts[1]));
        };
    }

    sealed interface Command permits Right, Left, Up, Down {
    }

    record Right(int amount) implements Command {
    }

    record Left(int amount) implements Command {
    }

    record Up(int amount) implements Command {
    }

    record Down(int amount) implements Command {
    }


    record Point(int x, int y) {
        boolean sameColumn(Point other) {
            return this.x == other.x;
        }

        boolean sameRow(Point other) {
            return this.y == other.y;
        }

        int xDistance(Point other) {
            return other.x - this.x;
        }

        int yDistance(Point other) {
            return other.y - this.y;
        }
    }


    static class Rope {
        private LinkedList<Point> points;

        public Rope(int size) {
            this.points = new LinkedList<>();
            for (int i = 0; i < size; i++) {
                points.add(new Point(0, 0));
            }
        }
    }

    /**
     * Imagine a Cartesian reference like this:
     * (0,0)
     * ------------------------------------------> x
     * |
     * |
     * V y
     * Going up reduces the y coordinate, going down increases it.
     * Going left reduce the x coordinate, going right increases it.
     * Both head and tail start at (0,0).
     */
    static class Journey {

        private Rope rope = new Rope(2);
        private Point head = new Point(0, 0);
        private Point tail = new Point(0, 0);
        private final Set<Point> tailTrail = new HashSet<>();
        private final Set<Point> tailTrail2 = new HashSet<>();

        public Journey() {
            tailTrail2.add(new Point(0, 0));
            tailTrail.add(tail);
        }

        public void accept(Command command) {
            switch (command) {
                case Left steps -> IntStream.range(1, steps.amount + 1).forEach(step -> {

                    head = new Point(head.x - 1, head.y);
                    tail = updatedFollower(tail, head);
                    addTrail(tail);
                });
                case Right steps -> IntStream.range(1, steps.amount + 1).forEach(step -> {
                    head = new Point(head.x + 1, head.y);
                    tail = updatedFollower(tail, head);
                    addTrail(tail);
                });
                case Up steps -> IntStream.range(1, steps.amount + 1).forEach(step -> {
                    head = new Point(head.x, head.y - 1);
                    tail = updatedFollower(tail, head);
                    addTrail(tail);
                });
                case Down steps -> IntStream.range(1, steps.amount + 1).forEach(step -> {
                    head = new Point(head.x, head.y + 1);
                    tail = updatedFollower(tail, head);
                    addTrail(tail);
                });
            }
        }

        private Point updatedFollower(Point follower, Point leader) {

            if (follower.sameRow(leader) && follower.xDistance(leader) > 1) {
                return new Point(follower.x + 1, follower.y);
            } else if (follower.sameColumn(leader) && follower.yDistance(leader) > 1) {
                return new Point(follower.x, follower.y + 1);
            } else if (follower.sameRow(leader) && follower.xDistance(leader) < -1) {
                return new Point(follower.x - 1, follower.y);
            } else if (follower.sameColumn(leader) && follower.yDistance(leader) < -1) {
                return new Point(follower.x, follower.y - 1);
            } else if (!follower.sameColumn(leader) && !follower.sameRow(leader)) {
                var xDistance = follower.xDistance(leader);
                var yDistance = follower.yDistance(leader);
                if (abs(xDistance) != 1 || abs(yDistance) != 1) {
                    var deltaX = computeDelta(xDistance);
                    var deltaY = computeDelta(yDistance);
                    return new Point(follower.x + deltaX, follower.y + deltaY);
                }
                return tail;
            } else {
                return tail;
            }
        }

        private int computeDelta(int distance) {
            if (distance < 0) {
                return -1;
            }
            return 1;
        }

        private void addTrail(Point newTail) {
            tailTrail.add(newTail);
        }

        public int howManyTailUniquePositions() {
            return tailTrail.size();
        }
    }

}
