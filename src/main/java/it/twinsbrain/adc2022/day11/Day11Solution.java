package it.twinsbrain.adc2022.day11;

import static it.twinsbrain.adc2022.FilesModule.read;
import static it.twinsbrain.adc2022.FilesModule.resource;
import static it.twinsbrain.adc2022.GroupingModule.chunked;
import static it.twinsbrain.adc2022.MathModule.lcm;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toList;

import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Day11Solution {

  public static void main(String[] args) throws URISyntaxException {
    var input = read(resource("/day11/input.txt"));
    System.out.printf("Part 1: %s", part1(input));
    System.out.println();
    System.out.printf("Part 2: %s", part2(input));
  }

  public static Long part2(List<String> input) {
    var monkeys = parse(input);
    var lcm = lcm(monkeys.stream().map(m -> m.throwDivisibleByTestValue).toList());
    Function<Long, Long> reduceStressFunction = l -> l % lcm;
    return monkeysWorkingHard(10_000, monkeys, reduceStressFunction);
  }

  public static Long part1(List<String> input) {
    var monkeys = parse(input);
    Function<Long, Long> reduceStressFunction = l -> l / 3;
    return monkeysWorkingHard(20, monkeys, reduceStressFunction);
  }

  public static Long monkeysWorkingHard(
      int rounds, List<Monkey> monkeys, Function<Long, Long> reduceStressFunction) {
    IntStream.range(0, rounds)
        .forEach(
            ignored ->
                monkeys.forEach(
                    monkey -> monkey.performInspectionsAndUpdate(monkeys, reduceStressFunction)));
    return monkeys.stream()
        .map(Monkey::numberOfInspections)
        .sorted(reverseOrder())
        .limit(2)
        .reduce((a, b) -> a * b)
        .orElse(0L);
  }

  static class Monkey {

    private final Queue<Long> itemsWorryLevels = new LinkedList<>();
    private final Function<Long, Long> updateWorryLevelFunction;
    public final int onThrowConditionPassed;
    public final int onThrowConditionFailed;
    public final Long throwDivisibleByTestValue;

    private long numberOfInspections = 0;

    public Monkey(
        Function<Long, Long> updateWorryLevelFunction,
        long throwDivisibleByTestValue,
        int onThrowConditionPassed,
        int onThrowConditionFailed,
        long... itemsWorryLevels) {
      this.throwDivisibleByTestValue = throwDivisibleByTestValue;
      this.updateWorryLevelFunction = updateWorryLevelFunction;
      this.onThrowConditionPassed = onThrowConditionPassed;
      this.onThrowConditionFailed = onThrowConditionFailed;
      for (long item : itemsWorryLevels) {
        this.itemsWorryLevels.add(item);
      }
    }

    public void performInspectionsAndUpdate(
        List<Monkey> monkeys, Function<Long, Long> reduceStressFunction) {
      while (!itemsWorryLevels.isEmpty()) {
        numberOfInspections++;
        var level = itemsWorryLevels.poll();
        long newLevel = reduceStressFunction.apply(updateWorryLevelFunction.apply(level));
        if (newLevel % throwDivisibleByTestValue == 0L) {
          monkeys.get(onThrowConditionPassed).addItem(newLevel);
        } else {
          monkeys.get(onThrowConditionFailed).addItem(newLevel);
        }
      }
    }

    public long numberOfInspections() {
      return numberOfInspections;
    }

    private void addItem(long newLevel) {
      itemsWorryLevels.add(newLevel);
    }
  }

  public static List<Monkey> parse(List<String> input) {
    return chunked(input, 7).stream().map(Day11Solution::toMonkey).collect(toList());
  }

  private static final Pattern updatePattern = Pattern.compile("old ([+, *]) (.*)");

  private static Monkey toMonkey(List<String> strings) {
    long[] itemsWorryLevels = itemsWorryLevels(strings);
    var updateFunction = toUpdateWorryLevelFunction(strings.get(2));
    var throwDivisor = toThrowDivisibleByTestValue(strings.get(3));
    var onPassed =
        Integer.parseInt(strings.get(4).replaceAll(" {4}If true: throw to monkey ", "").trim());
    var onFailed =
        Integer.parseInt(strings.get(5).replaceAll(" {4}If false: throw to monkey ", "").trim());
    return new Monkey(updateFunction, throwDivisor, onPassed, onFailed, itemsWorryLevels);
  }

  private static long[] itemsWorryLevels(List<String> strings) {
    String[] items = strings.get(1).replaceAll(" {2}Starting items: ", "").split(",");
    long[] result = new long[items.length];
    for (int i = 0; i < items.length; i++) {
      result[i] = Long.parseLong(items[i].trim());
    }
    return result;
  }

  private static long toThrowDivisibleByTestValue(String s) {
    return Long.parseLong(s.replaceAll(" {2}Test: divisible by ", ""));
  }

  private static Function<Long, Long> toUpdateWorryLevelFunction(String s) {
    String update = s.replaceAll(" {2}Operation: new = ", "");
    Matcher updateMatcher = updatePattern.matcher(update);
    updateMatcher.find();
    if (updateMatcher.group(1).equals("+")) {
      String operand = updateMatcher.group(2);
      if (operand.equals("old")) {
        return it -> it + it;
      } else {
        return it -> it + Long.parseLong(operand);
      }
    } else if (updateMatcher.group(1).equals("*")) {
      String operand = updateMatcher.group(2);
      if (operand.equals("old")) {
        return it -> it * it;
      } else {
        return it -> it * Long.parseLong(operand);
      }
    } else {
      throw new UnsupportedOperationException(s);
    }
  }
}
