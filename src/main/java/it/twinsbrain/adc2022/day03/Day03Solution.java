package it.twinsbrain.adc2022.day03;

import static it.twinsbrain.adc2022.GroupingModule.chunked;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Day03Solution {

  private static final Map<String, Integer> weights;

  static {
    record Pair(char c, int index) {}

    var count = new AtomicInteger(1);
    weights =
        IntStream.concat(IntStream.range(97, 123), IntStream.range(65, 91))
            .mapToObj(i -> new Pair((char) i, count.getAndIncrement()))
            .collect(toMap(p -> String.valueOf(p.c), p -> p.index));
  }

  static int part1PrioritiesSum(List<String> input) {
    return input.stream()
        .map(Day03Solution::representativeChar)
        .map(Day03Solution::toScore)
        .mapToInt(Integer::intValue)
        .sum();
  }

  private static int toScore(String key) {
    return weights.get(key);
  }

  private static String representativeChar(String s) {
    var length = s.length();
    var left = s.substring(0, length / 2);
    var right = s.substring(length / 2, length);

    var leftChars = charactersSet(left);
    for (char c : right.toCharArray()) {
      if (leftChars.contains(c)) {
        return String.valueOf(c);
      }
    }
    throw new IllegalStateException("Unexpected string format " + s);
  }

  private static Set<Character> charactersSet(String left) {
    return left.chars().mapToObj(i -> (char) i).collect(toSet());
  }

  static int part2PrioritiesSum(List<String> input) {
    return chunked(input, 3).stream()
        .map(Day03Solution::representativeChar)
        .map(Day03Solution::toScore)
        .mapToInt(Integer::intValue)
        .sum();
  }

  private static String representativeChar(List<String> elvesItems) {
    var chars = elvesItems.stream().map(Day03Solution::charactersSet).toList();
    var firstElfItems = chars.get(0);
    var secondElfItems = chars.get(1);
    var thirdElfItems = chars.get(2);
    for (var c : thirdElfItems) {
      if (firstElfItems.contains(c) && secondElfItems.contains(c)) {
        return String.valueOf(c);
      }
    }
    throw new IllegalStateException("Unexpected group format " + chars);
  }
}
