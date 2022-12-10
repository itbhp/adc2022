package it.twinsbrain.adc2022.day09;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.List;

import static it.twinsbrain.adc2022.FilesModule.read;
import static it.twinsbrain.adc2022.FilesModule.resource;
import static it.twinsbrain.adc2022.day09.Day09Solution.parseCommands;
import static it.twinsbrain.adc2022.day09.Day09Solution.tailPositions;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class Day09SolutionTest {

    private final List<String> input = List.of(
            "R 4",
            "U 4",
            "L 3",
            "D 1",
            "R 4",
            "D 1",
            "L 5",
            "R 2"
    );

    @Test
    void parse_commands_should_work() {
        var expectedCommands = List.of(
                new Day09Solution.Right(4),
                new Day09Solution.Up(4),
                new Day09Solution.Left(3),
                new Day09Solution.Down(1),
                new Day09Solution.Right(4),
                new Day09Solution.Down(1),
                new Day09Solution.Left(5),
                new Day09Solution.Right(2)
        );

        assertThat(parseCommands(input), equalTo(expectedCommands));
    }

    @Test
    void part1_should_work() {
        assertThat(tailPositions(input, 2), is(13));
    }

    @Nested
    class AcceptanceTest {

        private static final List<String> input;

        static {
            try {
                input = read(resource("/day09/input.txt"));
            } catch (URISyntaxException e) {
                throw new RuntimeException("input file not found!");
            }
        }

        @Test
        void part1_test() {
            assertThat(tailPositions(input, 2), equalTo(6563));
        }

        @Test
        void part2_test() {
            assertThat(tailPositions(input, 10), equalTo(2653));
        }
    }
}