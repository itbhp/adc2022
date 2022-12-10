package it.twinsbrain.adc2022.day10;

import it.twinsbrain.adc2022.day10.Day10Solution.Add;
import it.twinsbrain.adc2022.day10.Day10Solution.NoOp;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.twinsbrain.adc2022.day10.Day10Solution.parse;
import static it.twinsbrain.adc2022.day10.Day10Solution.part1;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class Day10SolutionTest {

    @Test
    void parse_should_work() {
        var input = List.of(
                "addx -8",
                "addx 13",
                "addx 4",
                "noop"
        );

        var expectedInstructions = List.of(
                new Add(-8),
                new Add(13),
                new Add(4),
                NoOp.INSTANCE
        );

        assertThat(parse(input), equalTo(expectedInstructions));
    }

    @Test
    void part1_should_work() {
        var input = List.of(
                "addx -8",
                "addx 13",
                "addx 4",
                "noop"
        );

        assertThat(part1(input), equalTo(7200));
    }
}