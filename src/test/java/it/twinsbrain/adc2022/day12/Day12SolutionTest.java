package it.twinsbrain.adc2022.day12;

import it.twinsbrain.adc2022.FixtureModule;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

class Day12SolutionTest {

    @Nested
    class AcceptanceTest {

        private final static List<String> input = FixtureModule.readSample("day12");

        @Test
        void part1_should_work() {
            assertThat(Day12Solution.part1(input), equalTo(31));
        }
    }
}