package it.twinsbrain.adc2022.day11;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static it.twinsbrain.adc2022.GroupingModule.chunked;

public class Day11Solution {

    static class Monkey {
        private final LinkedList<Integer> items = new LinkedList<>();
        private final Predicate<Integer> throwCondition;
        private final Function<Integer, Integer> updateFunction;
        public final int onThrowConditionPassed;
        public final int onThrowConditionFailed;

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
                this.items.add(item);
            }
        }

        public List<Integer> currentItems() {
            return items;
        }

        public boolean shouldThrow(Integer item) {
            return throwCondition.test(item);
        }

        public int updatedItem(Integer item) {
            return updateFunction.apply(item);
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
