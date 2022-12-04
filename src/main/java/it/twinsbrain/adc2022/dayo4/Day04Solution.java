package it.twinsbrain.adc2022.dayo4;

import java.net.URISyntaxException;
import java.util.List;

import static it.twinsbrain.adc2022.FilesModule.read;
import static it.twinsbrain.adc2022.FilesModule.resource;

public class Day04Solution {

    public static void main(String[] args) throws URISyntaxException {
        var input = read(resource("/day04/input.txt"));
        System.out.printf("Part 1: %d", howManyRangesOverlapsFully(input));
        System.out.println();
    }

    public static Long howManyRangesOverlapsFully(List<String> input) {
        return input.stream()
                .map(Day04Solution::toRangesPair)
                .filter(Day04Solution::fullyContaining)
                .count();
    }

    private static boolean fullyContaining(Pair pair) {
        Range first = pair.first;
        Range second = pair.second;
        return first.fullyContains(second) || second.fullyContains(first);
    }

    record Range(int start, int end) {
        public boolean fullyContains(Range second) {
            return this.start <= second.start && second.end <= this.end;
        }
    }

    record Pair(Range first, Range second) {
    }

    private static Pair toRangesPair(String line) {
        var parts = line.split(",");
        Range first = toRange(parts[0].split("-"));
        Range second = toRange(parts[1].split("-"));
        return new Pair(first, second);
    }

    private static Range toRange(String[] parts) {
        return new Range(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }
}
