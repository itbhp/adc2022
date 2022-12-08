package it.twinsbrain.adc2022.day08;

import org.junit.jupiter.api.Test;

import java.util.List;

import static it.twinsbrain.adc2022.day08.Day08Solution.parse;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Day08SolutionTest {

    @Test
    void itShouldParseTheGrid() {
        var input = List.of(
                "30373",
                "25512",
                "65332",
                "33549",
                "35390"
        );

        int[][] expected = new int[][]{
                {3, 0, 3, 7, 3},
                {2, 5, 5, 1, 2},
                {6, 5, 3, 3, 2},
                {3, 3, 5, 4, 9},
                {3, 5, 3, 9, 0}
        };

        assertArrayEquals(parse(input), expected);
    }
}