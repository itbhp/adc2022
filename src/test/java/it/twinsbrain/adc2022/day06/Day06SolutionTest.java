package it.twinsbrain.adc2022.day06;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static it.twinsbrain.adc2022.day06.Day06Solution.endPositionFirstMarker;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class Day06SolutionTest {

    @ParameterizedTest
    @CsvSource({
            "grvrmped, 6",
            "grvtsert, 4",
    })
    void itShouldWork(String input, int expectedPosition) {
        assertThat(endPositionFirstMarker(List.of(input)), equalTo(expectedPosition));
    }
}