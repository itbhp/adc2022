package it.twinsbrain.adc2022.day05;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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

    public static ArrayList<LinkedList<String>> parseCrates(List<String> input) {
        var cratesColumns = computeCratesColumns(input.get(0));
        var crates = newArrayList(cratesColumns);
        var regexp = Pattern.compile("\\[([A-Z])]\\s?");
        input.stream().takeWhile(row -> !row.matches("\\.*\\d+\\.*"))
                .forEach(line -> {
                    List<String> columnsItems = chunked(line, 4).toList();
                    for (int i = 0; i < columnsItems.size(); i++) {
                        var columnItem = columnsItems.get(i);
                        if (columnItem.trim().length() > 0) {
                            Matcher matcher = regexp.matcher(columnItem);
                            if (matcher.find()) {
                                var id = matcher.group(1);
                                crates.get(i).add(id);
                            }
                        }
                    }
                });
        return crates;
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
