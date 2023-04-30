package it.twinsbrain.adc2022.day06;

import static it.twinsbrain.adc2022.FilesModule.read;
import static it.twinsbrain.adc2022.FilesModule.resource;

import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day06Solution {

  public static void main(String[] args) throws URISyntaxException {
    var input = read(resource("/day06/input.txt"));
    System.out.printf("Part 1: %s", endPositionFirstMarker(input));
    System.out.println();
    System.out.printf("Part 2: %s", endPositionFirstMessageMarker(input));
  }

  public static int endPositionFirstMessageMarker(List<String> input) {
    return findMarker(input, 14);
  }

  public static int endPositionFirstMarker(List<String> input) {
    return findMarker(input, 4);
  }

  private static int findMarker(List<String> input, int size) {
    var buffer = input.get(0);
    var window = new DistinctElementsWindow(size);
    var index = new AtomicInteger(1);
    buffer
        .chars()
        .mapToObj(i -> (char) i)
        .takeWhile(c -> window.markerIsNotFound())
        .forEach(
            character -> {
              window.accept(character, index.intValue());
              index.getAndIncrement();
            });
    return window.lastIndex;
  }

  static class DistinctElementsWindow {
    private final LinkedList<Character> queue = new LinkedList<>();
    private final int size;
    private int lastIndex = -1;

    DistinctElementsWindow(int size) {
      this.size = size;
    }

    public void accept(Character c, int index) {
      if (queue.contains(c)) {
        while (queue.removeFirst() != c) {}
      }
      queue.add(c);
      lastIndex = index;
    }

    public boolean markerIsNotFound() {
      return queue.size() != size;
    }
  }
}
