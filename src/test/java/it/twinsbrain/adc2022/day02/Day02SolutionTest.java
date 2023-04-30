package it.twinsbrain.adc2022.day02;

import static it.twinsbrain.adc2022.FixtureModule.readSample;
import static it.twinsbrain.adc2022.day02.Day02Solution.score;
import static it.twinsbrain.adc2022.day02.Day02Solution.scoreWithExpectedOutcome;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class Day02SolutionTest {

  @ParameterizedTest
  @CsvSource({"A X, 4", "B Y, 5", "C Z, 6"})
  void oneRoundDraw(String input, int expectedScore) {
    assertThat(score(List.of(input)), equalTo(expectedScore));
  }

  // Rock 1, Paper 2, Scissor 3
  // A, X -> Rock, B, Y -> Paper, C,Z -> Scissor
  @ParameterizedTest
  @CsvSource({"C X, 7", "A Y, 8", "B Z, 9"})
  void oneRoundWon(String input, int expectedScore) {
    assertThat(score(List.of(input)), equalTo(expectedScore));
  }

  @ParameterizedTest
  @CsvSource({"B X, 1", "C Y, 2", "A Z, 3"})
  void oneRoundLost(String input, int expectedScore) {
    assertThat(score(List.of(input)), equalTo(expectedScore));
  }

  @Test
  void multipleRounds() {
    assertThat(score(List.of("C X", "B X")), equalTo(8));
  }

  @Nested
  class AcceptanceTest {
    private static final List<String> input = readSample("day02");

    @Test
    void part1_should_work() {
      assertThat(score(input), equalTo(15));
    }

    @Test
    void part2_should_work() {
      assertThat(scoreWithExpectedOutcome(input), equalTo(12));
    }
  }
}
