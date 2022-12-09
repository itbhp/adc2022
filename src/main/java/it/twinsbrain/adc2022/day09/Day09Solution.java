package it.twinsbrain.adc2022.day09;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day09Solution {

    public static int part1(List<String> input) {
        List<Command> commands = parseCommands(input);
        var journey = new Journey();
        commands.forEach(journey::accept);
        return journey.tailPositions();
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

        public void accept(Command command) {
            switch (command) {
                case Left steps -> head = new Point(head.x - steps.amount, head.y);
                case Right steps -> head = new Point(head.x + steps.amount, head.y);
                case Up steps -> head = new Point(head.x, head.y - steps.amount);
                case Down steps -> head = new Point(head.x, head.y + steps.amount);
            }
        }

        public int tailPositions() {
            return 0;
        }
    }

}
