package it.twinsbrain.adc2022.day01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.twinsbrain.adc2022.FixtureModule.readSample;
import static it.twinsbrain.adc2022.day01.Day01Solution.maxCaloriesByOneElf;
import static it.twinsbrain.adc2022.day01.Day01Solution.maxCaloriesByThreeElves;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("[Advent of Code 2022 - Day01]")
class Day01SolutionTest {

    @Nested
    @DisplayName("Part 1")
    class FirstPartTest {
        @Test
        void oneElfOneSnack() {
            var input = List.of("1235", "");
            assertThat(maxCaloriesByOneElf(input), equalTo(1235));
        }

        @Test
        void oneElfTwoSnacks() {
            var input = List.of("1235", "4", "");
            assertThat(maxCaloriesByOneElf(input), equalTo(1239));
        }

        @Test
        void twoElvesTwoSnacks() {
            var input = List.of("1235", "4", "", "7", "6", "");
            assertThat(maxCaloriesByOneElf(input), equalTo(1239));
        }
    }

    @Nested
    @DisplayName("Part 2")
    class SecondPartTopThreeElvesTest {
        @Test
        void oneElfOneSnack() {
            var input = List.of("1235", "");
            assertThat(maxCaloriesByThreeElves(input), equalTo(1235));
        }

        @Test
        void oneElfTwoSnacks() {
            var input = List.of("1235", "4", "");
            assertThat(maxCaloriesByThreeElves(input), equalTo(1239));
        }

        @Test
        void twoElvesTwoSnacks() {
            var input = List.of("1235", "4", "", "7", "6", "");
            assertThat(maxCaloriesByThreeElves(input), equalTo(1252));
        }

        @Test
        void fourElvesTwoSnacks() {
            var input = List.of("1235", "4", "", "7", "6", "", "8", "8", "", "2", "3", "");
            assertThat(maxCaloriesByThreeElves(input), equalTo(1268));
        }

        @Test
        void sevenElvesTwoSnacks() {
            var input =
                    List.of(
                            "1235",
                            "4",
                            "",
                            "7",
                            "6",
                            "",
                            "8",
                            "8",
                            "",
                            "2",
                            "3",
                            "",
                            "2000",
                            "3",
                            "",
                            "7",
                            "3",
                            "",
                            "4200",
                            "3",
                            "");
            assertThat(maxCaloriesByThreeElves(input), equalTo(7445));
        }
    }

    @Nested
    class AcceptanceTest {
        private static final List<String> input = readSample("day01");

        @Test
        void part1_should_work() {
            assertThat(maxCaloriesByOneElf(input), equalTo(24000));
        }

        @Test
        void part2_should_work() {
            assertThat(maxCaloriesByThreeElves(input), equalTo(45000));
        }
    }
}