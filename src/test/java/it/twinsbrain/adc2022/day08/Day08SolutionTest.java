package it.twinsbrain.adc2022.day08;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.List;

import static it.twinsbrain.adc2022.FilesModule.read;
import static it.twinsbrain.adc2022.FilesModule.resource;
import static it.twinsbrain.adc2022.day08.Day08Solution.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
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

    @Test
    void part1ShouldWork() {
        var input = List.of(
                "30373",
                "25512",
                "65332",
                "33549",
                "35390"
        );

        assertThat(howManyTreesAreVisible(input), is(21));
    }

    @Test
    void part2ShouldWork() {
        var input = List.of(
                "30373",
                "25512",
                "65332",
                "33549",
                "35390"
        );

        assertThat(highestScenicScore(input), is(8));
    }

    @Nested
    class AcceptanceTest {
        private static final List<String> input;

        static {
            try {
                input = read(resource("/day08/input.txt"));
            } catch (URISyntaxException e) {
                throw new RuntimeException("input file not found!");
            }
        }

        @Test
        void part1Test() {
            assertThat(howManyTreesAreVisible(input), is(1809));
        }

        @Test
        void part2Test() {
            assertThat(highestScenicScore(input), is(479400));
        }
    }
}