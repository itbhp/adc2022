package it.twinsbrain.adc2022.day03;

import org.junit.jupiter.api.Test;

import java.util.List;

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
                "CrZsJsPPZsGzwwsLwLmpwMDw" // s -> 19
        );

        // 16 + 38 + 42 + 22 + 20 + 19

        assertThat(Day03Solution.prioritiesSum(input), equalTo(157));
    }
}