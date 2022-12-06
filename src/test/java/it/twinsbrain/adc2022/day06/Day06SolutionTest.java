package it.twinsbrain.adc2022.day06;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.net.URISyntaxException;
import java.util.List;

import static it.twinsbrain.adc2022.FilesModule.read;
import static it.twinsbrain.adc2022.FilesModule.resource;
import static it.twinsbrain.adc2022.day06.Day06Solution.endPositionFirstMarker;
import static it.twinsbrain.adc2022.day06.Day06Solution.endPositionFirstMessageMarker;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class Day06SolutionTest {

    @ParameterizedTest
    @CsvSource({
            "grvrmped, 6",
            "grvtsert, 4",
    })
    void part1ShouldWork(String input, int expectedPosition) {
        assertThat(endPositionFirstMarker(List.of(input)), equalTo(expectedPosition));
    }

    @ParameterizedTest
    @CsvSource({
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb, 19",
            "bvwbjplbgvbhsrlpgdmjqwftvncz, 23",
    })
    void part2ShouldWork(String input, int expectedPosition) {
        assertThat(endPositionFirstMessageMarker(List.of(input)), equalTo(expectedPosition));
    }

    @Nested
    class AcceptanceTest {
        private static final List<String> input;

        static {
            try {
                input = read(resource("/day06/input.txt"));
            } catch (URISyntaxException e) {
                throw new RuntimeException("input file not found!");
            }
        }

        @Test
        void part1Test() {
            assertThat(endPositionFirstMarker(input), equalTo(1480));
        }

        @Test
        void part2Test() {
            assertThat(endPositionFirstMessageMarker(input), equalTo(2746));
        }
    }
}