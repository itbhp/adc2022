package it.twinsbrain.adc2022.day03;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.twinsbrain.adc2022.FixtureModule.readSample;
import static it.twinsbrain.adc2022.day03.Day03Solution.part1PrioritiesSum;
import static it.twinsbrain.adc2022.day03.Day03Solution.part2PrioritiesSum;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class Day03SolutionTest {

    @Test
    void itShouldWork() {
        var input = List.of(
                "vJrwpWtwJgWrhcsFMMfFFhFp", // p -> 16
                "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL", // L -> 38
                "PmmdzqPrVvPwwTWBwg", // P -> 42
                "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn", // v -> 22
                "ttgJtRGJQctTZtZT", // t -> 20
                "CrZsJsPPZsGzwwsLwLmpwMDw" // s -> 9
        );

        // 16 + 38 + 42 + 22 + 20 + 19

        assertThat(part1PrioritiesSum(input), equalTo(157));
    }

    @Nested
    class AcceptanceTest {
        private static final List<String> input = readSample("day03");

        @Test
        void part1_should_work() {
            assertThat(part1PrioritiesSum(input), equalTo(157));
        }

        @Test
        void part2_should_work() {
            assertThat(part2PrioritiesSum(input), equalTo(70));
        }
    }
}