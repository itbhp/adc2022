package it.twinsbrain.adc2022.day12;

import it.twinsbrain.adc2022.FixtureModule;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.twinsbrain.adc2022.day12.Day12Solution.parse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class Day12SolutionTest {

    @Test
    void parse_should_work() {
        var input = List.of(
                "Sabc",
                "bssE"
        );
        var chars = new char[2][4];
        chars[0] = new char[]{'S', 'a', 'b', 'c'};
        chars[1] = new char[]{'b', 's', 's', 'E'};
        var expectedGrid = new Day12Solution.Grid(chars, 0, 0, 1, 3);

        assertThat(parse(input), equalTo(expectedGrid));
    }

    @Nested
    class AcceptanceTest {

        private final static List<String> input = FixtureModule.readSample("day12");

        @Test
        void part1_should_work() {
            assertThat(Day12Solution.part1(input), equalTo(31));
        }
    }
}