package it.twinsbrain.adc2022;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupingModule {

    public static <T> Collection<List<T>> chunked(List<T> inputList, int chunkSize) {
        AtomicInteger counter = new AtomicInteger();
        return inputList.stream().collect(Collectors.groupingBy(it -> counter.getAndIncrement() / chunkSize)).values();
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
