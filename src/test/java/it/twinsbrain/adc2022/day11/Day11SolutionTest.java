package it.twinsbrain.adc2022.day11;

import org.junit.jupiter.api.Test;

import java.util.List;

import static it.twinsbrain.adc2022.day11.Day11Solution.parse;
import static it.twinsbrain.adc2022.day11.Day11Solution.part1;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

class Day11SolutionTest {

    private final List<String> input = List.of(
            "Monkey 0:",
            "  Starting items: 79, 98",
            "  Operation: new = old * 19",
            "  Test: divisible by 23",
            "    If true: throw to monkey 2",
            "    If false: throw to monkey 3",
            "",
            "Monkey 1:",
            "  Starting items: 54, 65, 75, 74",
            "  Operation: new = old + 6",
            "  Test: divisible by 19",
            "    If true: throw to monkey 2",
            "    If false: throw to monkey 0",
            "",
            "Monkey 2:",
            "  Starting items: 79, 60, 97",
            "  Operation: new = old * old",
            "  Test: divisible by 13",
            "    If true: throw to monkey 1",
            "    If false: throw to monkey 3",
            "",
            "Monkey 3:",
            "  Starting items: 74",
            "  Operation: new = old + 3",
            "  Test: divisible by 17",
            "    If true: throw to monkey 0",
            "    If false: throw to monkey 1"
    );

    @Test
    void parse_is_working() {
        var monkeys = parse(input);
        assertThat(monkeys.size(), is(4));
        var monkey0 = monkeys.get(0);
        var monkey1 = monkeys.get(1);
        var monkey2 = monkeys.get(2);
        var monkey3 = monkeys.get(3);
        assertThat(monkey0.currentItems(), containsInAnyOrder(79, 98));
        assertThat(monkey0.shouldThrow(23), is(true));
        assertThat(monkey0.shouldThrow(46), is(true));
        assertThat(monkey0.shouldThrow(10), is(false));
        assertThat(monkey0.updatedItem(10), is(190));
        assertThat(monkey0.onThrowConditionPassed, is(2));
        assertThat(monkey0.onThrowConditionFailed, is(3));
        assertThat(monkey1.currentItems(), containsInAnyOrder(54, 65, 75, 74));
        assertThat(monkey1.shouldThrow(19), is(true));
        assertThat(monkey1.shouldThrow(190), is(true));
        assertThat(monkey1.shouldThrow(10), is(false));
        assertThat(monkey1.updatedItem(10), is(16));
        assertThat(monkey1.onThrowConditionPassed, is(2));
        assertThat(monkey1.onThrowConditionFailed, is(0));
        assertThat(monkey2.currentItems(), containsInAnyOrder(79, 60, 97));
        assertThat(monkey2.shouldThrow(13), is(true));
        assertThat(monkey2.shouldThrow(26), is(true));
        assertThat(monkey2.shouldThrow(10), is(false));
        assertThat(monkey2.updatedItem(10), is(100));
        assertThat(monkey2.onThrowConditionPassed, is(1));
        assertThat(monkey2.onThrowConditionFailed, is(3));
        assertThat(monkey3.currentItems(), containsInAnyOrder(74));
        assertThat(monkey3.shouldThrow(17), is(true));
        assertThat(monkey3.shouldThrow(34), is(true));
        assertThat(monkey3.shouldThrow(10), is(false));
        assertThat(monkey3.updatedItem(10), is(13));
        assertThat(monkey3.onThrowConditionPassed, is(0));
        assertThat(monkey3.onThrowConditionFailed, is(1));
    }

    @Test
    void part1_should_work() {
        assertThat(part1(input), is(10605));
    }
}