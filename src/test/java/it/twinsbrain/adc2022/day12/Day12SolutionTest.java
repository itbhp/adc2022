package it.twinsbrain.adc2022.day12;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import it.twinsbrain.adc2022.FixtureModule;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class Day12SolutionTest {

  @Nested
  class AcceptanceTest {

    private static final List<String> input = FixtureModule.readSample("day12");

    @Test
    void part1_should_work() {
      assertThat(Day12Solution.part1(input), equalTo(31));
    }

    @Test
    void part2_should_work() {
      assertThat(Day12Solution.part2(input), equalTo(29));
    }
  }
}
