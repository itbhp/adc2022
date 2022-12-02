package it.twinsbrain.adc2022.day01;

import org.junit.jupiter.api.Test;

import java.util.List;

import static it.twinsbrain.adc2022.day01.Day01Solution.maxCalories;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class Day01SolutionTest {

    @Test
    void oneElfOneSnack() {
        var input = List.of("1235");
        assertThat(maxCalories(input), equalTo(1235));
    }
}