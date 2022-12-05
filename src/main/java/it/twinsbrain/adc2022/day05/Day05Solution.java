package it.twinsbrain.adc2022.day05;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day05Solution {

    public static void main(String[] args) {
        Deque<Integer> l = new LinkedList<>();
        l.add(1);
        l.add(2);
        l.add(3);

        System.out.println(l.peek());

        l.removeFirst();

        System.out.println(l.peek());

        l.addFirst(4);

        System.out.println(l.peek());
    }

    record Command(int howMany, int from, int to) {
    }

    public static List<Command> parseCommands(List<String> input) {
        var regexp = Pattern.compile("move (\\d) from (\\d) to (\\d)");
        return input.stream()
                .dropWhile(line -> !line.contains("move"))
                .map(line -> {
                    var matcher = regexp.matcher(line);
                    if (matcher.find()) {
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
                if (matcher.find()) {
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
