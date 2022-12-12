package it.twinsbrain.adc2022.day12;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class Day12SolutionTest {

    @Test
    void parse_should_work() {
        var input = List.of(
                "Sabc",
                "bssE"
        );
        var expected = new char[2][4];
        expected[0] = new char[]{'S', 'a', 'b', 'c'};
        expected[1] = new char[]{'b', 's', 's', 'E'};

        assertThat(Day12Solution.parse(input), equalTo(expected));
    }
}