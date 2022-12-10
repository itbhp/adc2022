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
        System.out.printf("Part 1: %s", tailPositions(input, 2));
        System.out.println();
        System.out.printf("Part 2: %s", tailPositions(input, 10));
    }

    public static int tailPositions(List<String> input, int numberOfKnots) {
        List<Command> commands = parseCommands(input);
        var journey = new Journey(numberOfKnots);
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

        private final LinkedList<Point> queue;
        private final Set<Point> tailTrail = new HashSet<>();

        public Journey(int numberOfKnots) {
            queue = new LinkedList<>();
            for (int i = 0; i < numberOfKnots; i++) {
                queue.add(new Point(0, 0));
            }
            tailTrail.add(new Point(0, 0));
        }

        public void accept(Command command) {
            switch (command) {
                case Left steps -> IntStream.range(1, steps.amount + 1).forEach(step -> {
                    Point head = queue.get(0);
                    queue.set(0, new Point(head.x - 1, head.y));
                    for (int i = 1; i < queue.size(); i++) {
                        queue.set(i, updatedFollower(queue.get(i), queue.get(i - 1)));
                    }
                    addTrail(queue.getLast());
                });
                case Right steps -> IntStream.range(1, steps.amount + 1).forEach(step -> {
                    Point head = queue.get(0);
                    queue.set(0, new Point(head.x + 1, head.y));
                    for (int i = 1; i < queue.size(); i++) {
                        queue.set(i, updatedFollower(queue.get(i), queue.get(i - 1)));
                    }
                    addTrail(queue.getLast());
                });
                case Up steps -> IntStream.range(1, steps.amount + 1).forEach(step -> {
                    Point head = queue.get(0);
                    queue.set(0, new Point(head.x, head.y - 1));
                    for (int i = 1; i < queue.size(); i++) {
                        queue.set(i, updatedFollower(queue.get(i), queue.get(i - 1)));
                    }
                    addTrail(queue.getLast());
                });
                case Down steps -> IntStream.range(1, steps.amount + 1).forEach(step -> {
                    Point head = queue.get(0);
                    queue.set(0, new Point(head.x, head.y + 1));
                    for (int i = 1; i < queue.size(); i++) {
                        queue.set(i, updatedFollower(queue.get(i), queue.get(i - 1)));
                    }
                    addTrail(queue.getLast());
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
                return follower;

            } else {

                return follower;
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
