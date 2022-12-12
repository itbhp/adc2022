package it.twinsbrain.adc2022.day04;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.twinsbrain.adc2022.FixtureModule.readSample;
import static it.twinsbrain.adc2022.day04.Day04Solution.howManyRangesOverlaps;
import static it.twinsbrain.adc2022.day04.Day04Solution.howManyRangesOverlapsFully;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day04SolutionTest {

    @Test
    void fullyContaining() {
        var input = List.of("2-4,5-6", "1-2,2-3", "2-6,1-6");
        assertThat(howManyRangesOverlapsFully(input), equalTo(1L));
    }

    @Test
    void overlapping() {
        var input = List.of("2-4,5-6", "1-2,2-3", "2-6,1-6");
        assertThat(howManyRangesOverlaps(input), equalTo(2L));
    }

    @Nested
    class AcceptanceTest {

        private static final List<String> input = readSample("day04");

        @Test
        void part1() {
            assertThat(howManyRangesOverlapsFully(input), equalTo(2L));
        }

        @Test
        void part2() {
            assertThat(howManyRangesOverlaps(input), equalTo(4L));
        }
    }
}
