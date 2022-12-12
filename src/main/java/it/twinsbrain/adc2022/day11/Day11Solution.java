package it.twinsbrain.adc2022.day11;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static it.twinsbrain.adc2022.GroupingModule.chunked;

public class Day11Solution {

    public static int part1(List<String> input) {
        var monkeys = parse(input);
        IntStream.range(0, 20).forEach(ignored ->
                monkeys.forEach(monkey -> monkey.performInspectionsAndUpdate(monkeys))
        );
        return monkeys.stream()
                .map(Monkey::numberOfInspections)
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .mapToInt(Integer::intValue)
                .reduce((a, b) -> a * b).orElse(0);
    }

    static class Monkey {
        private final Queue<Integer> worryLevelsItems = new LinkedList<>();
        private final Predicate<Integer> throwCondition;
        private final Function<Integer, Integer> updateFunction;
        public final int onThrowConditionPassed;
        public final int onThrowConditionFailed;

        private int numberOfInspections = 0;

        public Monkey(
                Predicate<Integer> throwCondition,
                Function<Integer, Integer> updateFunction,
                int onThrowConditionPassed,
                int onThrowConditionFailed,
                int... items
        ) {
            this.throwCondition = throwCondition;
            this.updateFunction = updateFunction;
            this.onThrowConditionPassed = onThrowConditionPassed;
            this.onThrowConditionFailed = onThrowConditionFailed;
            for (int item : items) {
                this.worryLevelsItems.add(item);
            }
        }

        public List<Integer> currentItems() {
            return new LinkedList<>(worryLevelsItems);
        }

        public boolean shouldThrow(Integer item) {
            return throwCondition.test(item);
        }

        public int updatedItem(Integer item) {
            return updateFunction.apply(item);
        }

        public void performInspectionsAndUpdate(List<Monkey> monkeys) {
            while (!worryLevelsItems.isEmpty()) {
                numberOfInspections++;
                var level = worryLevelsItems.poll();
                int newLevel = updateFunction.apply(level) / 3;
                if (throwCondition.test(newLevel)) {
                    monkeys.get(onThrowConditionPassed).addItem(newLevel);
                } else {
                    monkeys.get(onThrowConditionFailed).addItem(newLevel);
                }
            }
        }

        public int numberOfInspections() {
            return numberOfInspections;
        }

        private void addItem(int newLevel) {
            worryLevelsItems.add(newLevel);
        }
    }

    public static List<Monkey> parse(List<String> input) {
        return chunked(input, 7).stream()
                .map(Day11Solution::toMonkey)
                .collect(Collectors.toList());
    }

    private static final Pattern updatePattern = Pattern.compile("old ([+, *]) (.*)");

    private static Monkey toMonkey(List<String> strings) {
        String[] items = strings.get(1).replaceAll("\s{2}Starting items: ", "").split(",");
        var updateFunction = toUpdateFunction(strings.get(2));
        var throwCondition = toThrowCondition(strings.get(3));
        var onPassed = Integer.parseInt(strings.get(4).replaceAll("\s{4}If true: throw to monkey ", "").trim());
        var onFailed = Integer.parseInt(strings.get(5).replaceAll("\s{4}If false: throw to monkey ", "").trim());
        return new Monkey(
                throwCondition,
                updateFunction,
                onPassed,
                onFailed,
                Arrays.stream(items).mapToInt(it -> Integer.parseInt(it.trim())).toArray()
        );
    }

    private static Predicate<Integer> toThrowCondition(String s) {
        int divisibleBy = Integer.parseInt(s.replaceAll("\s{2}Test: divisible by ", ""));
        return it -> it % divisibleBy == 0;
    }

    private static Function<Integer, Integer> toUpdateFunction(String s) {
        String update = s.replaceAll("\s{2}Operation: new = ", "");
        Matcher updateMatcher = updatePattern.matcher(update);
        updateMatcher.find();
        if (updateMatcher.group(1).equals("+")) {
            String operand = updateMatcher.group(2);
            if (operand.equals("old")) {
                return it -> it + it;
            } else {
                return it -> it + Integer.parseInt(operand);
            }
        } else if (updateMatcher.group(1).equals("*")) {
            String operand = updateMatcher.group(2);
            if (operand.equals("old")) {
                return it -> it * it;
            } else {
                return it -> it * Integer.parseInt(operand);
            }
        } else {
            throw new UnsupportedOperationException(s);
        }
    }
}
