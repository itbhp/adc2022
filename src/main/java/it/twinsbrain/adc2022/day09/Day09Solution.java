package it.twinsbrain.adc2022.day09;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.abs;

public class Day09Solution {

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
        private Point head = new Point(0, 0);
        private Point tail = new Point(0, 0);
        private final Set<Point> tailTrail = new HashSet<>();

        public Journey() {
            tailTrail.add(tail);
        }

        public void accept(Command command) {
            switch (command) {
                case Left steps -> IntStream.range(1, steps.amount + 1).forEach(step -> {
                    head = new Point(head.x - 1, head.y);
                    updateTail();
                });
                case Right steps -> IntStream.range(1, steps.amount + 1).forEach(step -> {
                    head = new Point(head.x + 1, head.y);
                    updateTail();
                });
                case Up steps -> IntStream.range(1, steps.amount + 1).forEach(step -> {
                    head = new Point(head.x, head.y - 1);
                    updateTail();
                });
                case Down steps -> IntStream.range(1, steps.amount + 1).forEach(step -> {
                    head = new Point(head.x, head.y + 1);
                    updateTail();
                });
            }
        }

        private void updateTail() {

            if (tail.sameRow(head) && tail.xDistance(head) > 1) {
                var newTail = new Point(tail.x + 1, tail.y);
                tail = newTail;
                addTrail(newTail);
            } else if (tail.sameColumn(head) && tail.yDistance(head) > 1) {
                var newTail = new Point(tail.x, tail.y + 1);
                tail = newTail;
                addTrail(newTail);
            } else if (tail.sameRow(head) && tail.xDistance(head) < -1) {
                var newTail = new Point(tail.x - 1, tail.y);
                tail = newTail;
                addTrail(newTail);
            } else if (tail.sameColumn(head) && tail.yDistance(head) < -1) {
                var newTail = new Point(tail.x, tail.y - 1);
                tail = newTail;
                addTrail(newTail);
            } else if (!tail.sameColumn(head) && !tail.sameRow(head)) {
                var xDistance = tail.xDistance(head);
                var yDistance = tail.yDistance(head);
                if (abs(xDistance) != 1 || abs(yDistance) != 1) {
                    var deltaX = computeDelta(xDistance);
                    var deltaY = computeDelta(yDistance);
                    var newTail = new Point(tail.x + deltaX, tail.y + deltaY);
                    tail = newTail;
                    addTrail(newTail);
                }
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
