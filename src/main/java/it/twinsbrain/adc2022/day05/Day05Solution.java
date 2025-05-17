package it.twinsbrain.adc2022.day05;

import static it.twinsbrain.adc2022.FilesModule.read;
import static it.twinsbrain.adc2022.FilesModule.resource;
import static it.twinsbrain.adc2022.GroupingModule.chunked;

import java.net.URISyntaxException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day05Solution {

  public static void main(String[] args) throws URISyntaxException {
    var input = read(resource("/day05/input.txt"));
    System.out.printf("Part 1: %s", part1(input));
    System.out.println();
    System.out.printf("Part 2: %s", part2(input));
  }

  public static String part2(List<String> input) {
    return topElementsAfterMoving(input, Day05Solution::part2ApplyCommands);
  }

  private static void part2ApplyCommands(
      List<LinkedList<String>> cratesColumns, List<Command> commands) {
    for (Command command : commands) {
      var fromColumn = cratesColumns.get(command.from - 1);
      var toColumn = cratesColumns.get(command.to - 1);
      var stack = new LinkedList<String>();
      swap(fromColumn, stack, command.howMany);
      swap(stack, toColumn, command.howMany);
    }
  }

  public static String part1(List<String> input) {
    return topElementsAfterMoving(input, Day05Solution::part1ApplyCommands);
  }

  private static void part1ApplyCommands(
      List<LinkedList<String>> cratesColumns, List<Command> commands) {
    for (Command command : commands) {
      var fromColumn = cratesColumns.get(command.from - 1);
      var toColumn = cratesColumns.get(command.to - 1);
      swap(fromColumn, toColumn, command.howMany);
    }
  }

  private static void swap(LinkedList<String> from, LinkedList<String> to, int howMany) {
    for (int i = 0; i < howMany; i++) {
      if (!from.isEmpty()) {
        to.push(from.pop());
      }
    }
  }

  public static String topElementsAfterMoving(
      List<String> input, BiConsumer<List<LinkedList<String>>, List<Command>> strategy) {
    var cratesColumns = parseCrates(input);
    var commands = parseCommands(input);
    strategy.accept(cratesColumns, commands);
    return cratesColumns.stream()
        .map(LinkedList::peek)
        .filter(Objects::nonNull)
        .collect(Collectors.joining());
  }

  public record Command(int howMany, int from, int to) {
  }

  public static List<Command> parseCommands(List<String> input) {
    var regexp = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");
    return input.stream()
        .dropWhile(line -> !line.contains("move"))
        .map(
            line -> {
              var matcher = regexp.matcher(line);
              if (matcher.matches()) {
                var command =
                    new Command(
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3)));
                return Optional.of(command);
              } else {
                return Optional.<Command>empty();
              }
            })
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();
  }

  public static List<LinkedList<String>> parseCrates(List<String> input) {
    var cratesColumns = computeCratesColumns(input.getFirst());
    var crates = newArrayList(cratesColumns);
    var regexp = Pattern.compile("\\[([A-Z])]\\s?");
    BiConsumer<Integer, String> addElemToCrates = (i, id) -> crates.get(i).add(id);
    input.stream()
        .takeWhile(row -> !row.matches("\\.*\\d+\\.*"))
        .forEach(line -> addColumnItemsToCrates(regexp, addElemToCrates, line));
    return crates;
  }

  private static void addColumnItemsToCrates(
      Pattern regexp, BiConsumer<Integer, String> addElemToCrates, String line) {
    var columnsItems = chunked(line, 4).toList();
    for (int i = 0; i < columnsItems.size(); i++) {
      var columnItem = columnsItems.get(i);
      if (!columnItem.trim().isEmpty()) {
        var matcher = regexp.matcher(columnItem);
        if (matcher.matches()) {
          var id = matcher.group(1);
          addElemToCrates.accept(i, id);
        }
      }
    }
  }

  private static int computeCratesColumns(String row) {
    var length = row.length();
    var div = length / 4;
    return div * 4 == length ? div : div + 1;
  }

  private static ArrayList<LinkedList<String>> newArrayList(int cratesColumns) {
    var linkedLists = new ArrayList<LinkedList<String>>(cratesColumns);
    for (int i = 0; i < cratesColumns; i++) {
      linkedLists.add(new LinkedList<>());
    }
    return linkedLists;
  }
}
