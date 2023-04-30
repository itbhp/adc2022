package it.twinsbrain.adc2022.day10;

import static it.twinsbrain.adc2022.FixtureModule.readSample;
import static it.twinsbrain.adc2022.day10.Day10Solution.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day10SolutionTest {

  private static final List<String> acceptanceInput = readSample("day10");

  @Test
  void parse_should_work() {
    var input = List.of("addx -8", "addx 13", "addx 4", "noop");

    var expectedInstructions = List.of(new Add(-8), new Add(13), new Add(4), NoOp.INSTANCE);

    assertThat(parse(input), equalTo(expectedInstructions));
  }

  @Test
  void part1_should_work() {
    var input = List.of("addx -8", "addx 13", "addx 4", "noop");

    assertThat(part1(input), equalTo(7200));
  }

  @Test
  void acceptanceTestPart1() {
    assertThat(part1(acceptanceInput), equalTo(13140));
  }

  @Test
  void acceptanceTestPart2() {
    part2(acceptanceInput);
  }
}
