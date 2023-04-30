package it.twinsbrain.adc2022.day01;

import static java.util.Collections.reverseOrder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day01Solution {

  public static int maxCaloriesByThreeElves(List<String> input) {
    return topCalories(input, 3);
  }

  public static int maxCaloriesByOneElf(List<String> input) {
    return topCalories(input, 1);
  }

  private static int topCalories(List<String> input, int howMany) {
    return caloriesByElf(input)
        .sorted(reverseOrder())
        .limit(howMany)
        .mapToInt(Integer::intValue)
        .sum();
  }

  private static Stream<Integer> caloriesByElf(List<String> input) {
    String[] caloriesByElf = String.join("\n", input).split("\n\n");
    return Arrays.stream(caloriesByElf).mapToInt(Day01Solution::singleElfCalories).boxed();
  }

  private static int singleElfCalories(String s) {
    return Arrays.stream(s.split("\n")).mapToInt(Integer::valueOf).sum();
  }
}
