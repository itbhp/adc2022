package it.twinsbrain.adc2022.day05;

import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static it.twinsbrain.adc2022.FilesModule.read;
import static it.twinsbrain.adc2022.FilesModule.resource;

public class Day05Solution {

    public static void main(String[] args) throws URISyntaxException {
        var input = read(resource("/day05/input.txt"));
        System.out.printf("Part 1: %s", part1(input));
        System.out.println();
    }

    public static String part2(List<String> input) {
        return topElementsAfterMoving(input, Day05Solution::part2ApplyCommands);
    }

    private static void part2ApplyCommands(
            List<LinkedList<String>> cratesColumns,
            List<Command> commands
    ) {
        for (Command command : commands) {
            var from = cratesColumns.get(command.from - 1);
            var to = cratesColumns.get(command.to - 1);
            var stack = new Stack<String>();
            for (int i = 0; i < command.howMany; i++) {
                if (!from.isEmpty()) {
                    stack.push(from.pop());
                }
            }
            for (int i = 0; i < command.howMany; i++) {
                if (!stack.isEmpty()) {
                    to.push(stack.pop());
                }
            }
        }
    }

    public static String part1(List<String> input) {
        return topElementsAfterMoving(input, Day05Solution::part1ApplyCommands);
    }

    public static String topElementsAfterMoving(
            List<String> input,
            BiConsumer<List<LinkedList<String>>, List<Command>> strategy
    ) {
        var cratesColumns = parseCrates(input);
        var commands = parseCommands(input);
        strategy.accept(cratesColumns, commands);
        return cratesColumns.stream()
                .map(LinkedList::peek)
                .filter(Objects::nonNull)
                .collect(Collectors.joining());
    }

    private static void part1ApplyCommands(List<LinkedList<String>> cratesColumns, List<Command> commands) {
        for (Command command : commands) {
            var from = cratesColumns.get(command.from - 1);
            var to = cratesColumns.get(command.to - 1);
            for (int i = 0; i < command.howMany; i++) {
                if (!from.isEmpty()) {
                    to.addFirst(from.removeFirst());
                }
            }
        }
    }

    record Command(int howMany, int from, int to) {
    }

    public static List<Command> parseCommands(List<String> input) {
        var regexp = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");
        return input.stream()
                .dropWhile(line -> !line.contains("move"))
                .map(line -> {
                    var matcher = regexp.matcher(line);
                    if (matcher.matches()) {
                        Command command = new Command(
                                Integer.parseInt(matcher.group(1)),
                                Integer.parseInt(matcher.group(2)),
                                Integer.parseInt(matcher.group(3))
                        );
                        return Optional.of(command);
                    } else {
                        return Optional.<Command>empty();
                    }
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public static ArrayList<LinkedList<String>> parseCrates(List<String> input) {
        var cratesColumns = computeCratesColumns(input.get(0));
        var crates = newArrayList(cratesColumns);
        var regexp = Pattern.compile("\\[([A-Z])]\\s?");
        BiFunction<Integer, String, Void> addElemToCrates = (Integer i, String id) -> {
            crates.get(i).add(id);
            return null;
        };
        input.stream()
                .takeWhile(row -> !row.matches("\\.*\\d+\\.*"))
                .forEach(line -> addColumnItemsToCrates(regexp, addElemToCrates, line));
        return crates;
    }

    private static void addColumnItemsToCrates(
            Pattern regexp,
            BiFunction<Integer, String, Void> addElemToCrates,
            String line
    ) {
        List<String> columnsItems = chunked(line, 4).toList();
        for (int i = 0; i < columnsItems.size(); i++) {
            var columnItem = columnsItems.get(i);
            if (columnItem.trim().length() > 0) {
                Matcher matcher = regexp.matcher(columnItem);
                if (matcher.matches()) {
                    var id = matcher.group(1);
                    addElemToCrates.apply(i, id);
                }
            }
        }
    }

    private static int computeCratesColumns(String row) {
        int length = row.length();
        int div = length / 4;
        return div * 4 == length ? div : div + 1;
    }

    private static ArrayList<LinkedList<String>> newArrayList(int cratesColumns) {
        ArrayList<LinkedList<String>> linkedLists = new ArrayList<>(cratesColumns);
        for (int i = 0; i < cratesColumns; i++) {
            linkedLists.add(new LinkedList<>());
        }
        return linkedLists;
    }

    public static Stream<String> chunked(String input, int chunkSize) {
        AtomicInteger counter = new AtomicInteger();
        return input
                .chars()
                .mapToObj(i -> (char) i)
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / chunkSize))
                .values()
                .stream()
                .map(l -> l.stream().map(String::valueOf).collect(Collectors.joining()));
    }
}
