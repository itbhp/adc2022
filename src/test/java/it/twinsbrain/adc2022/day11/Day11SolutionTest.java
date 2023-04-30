package it.twinsbrain.adc2022.day11;

import static it.twinsbrain.adc2022.day11.Day11Solution.part1;
import static it.twinsbrain.adc2022.day11.Day11Solution.part2;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day11SolutionTest {

  private final List<String> input =
      List.of(
          "Monkey 0:",
          "  Starting items: 79, 98",
          "  Operation: new = old * 19",
          "  Test: divisible by 23",
          "    If true: throw to monkey 2",
          "    If false: throw to monkey 3",
          "",
          "Monkey 1:",
          "  Starting items: 54, 65, 75, 74",
          "  Operation: new = old + 6",
          "  Test: divisible by 19",
          "    If true: throw to monkey 2",
          "    If false: throw to monkey 0",
          "",
          "Monkey 2:",
          "  Starting items: 79, 60, 97",
          "  Operation: new = old * old",
          "  Test: divisible by 13",
          "    If true: throw to monkey 1",
          "    If false: throw to monkey 3",
          "",
          "Monkey 3:",
          "  Starting items: 74",
          "  Operation: new = old + 3",
          "  Test: divisible by 17",
          "    If true: throw to monkey 0",
          "    If false: throw to monkey 1");

  @Test
  void part1_should_work() {
    assertThat(part1(input), is(10605L));
  }

  @Test
  void part2_should_work() {
    assertThat(part2(input), is(2713310158L));
  }
}
