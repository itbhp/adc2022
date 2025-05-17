package it.twinsbrain.adc2022.day13;

import static it.twinsbrain.adc2022.GroupingModule.chunked;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Day13Solution {

  public static List<Packet> parse(List<String> input) {
    return chunked(input, 3).stream().map(Day13Solution::toPacket)
        .toList();
  }

  private static Packet toPacket(List<String> strings) {
    var left = strings.get(0);
    var right = strings.get(1);
    return new Packet(toItems(left), toItems(right));
  }

  private static MultipleItems toItems(String source) {
    var result = new MultipleItems();
    var container = result;
    var stack = new LinkedList<MultipleItems>();
    stack.push(container);
    for (int i = 1; i < source.length() - 1; i++) {
      var c = source.substring(i, i + 1);
      switch (c) {
        case "[" -> {
          var newCurrent = new MultipleItems();
          assert container != null;
          container.addItem(newCurrent);
          container = newCurrent;
          stack.push(container);
        }
        case "]" -> {
          stack.pop();
          container = stack.peek();
        }
        case "," -> {}
        default -> {
          assert container != null;
          container.addItem(new it.twinsbrain.adc2022.day13.Day13Solution.SingleItem(Integer.parseInt(c)));
        }
      }
    }
    return result;
  }

  record Packet(MultipleItems left, MultipleItems right) {}

  sealed interface PacketItem {}

  record SingleItem(int value) implements PacketItem {}

  static final class MultipleItems implements PacketItem {
    private final List<PacketItem> items;

    public MultipleItems() {
      this(new ArrayList<>());
    }

    public MultipleItems(List<PacketItem> items) {
      this.items = items;
    }

    public void addItem(PacketItem item) {
      items.add(item);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      var that = (MultipleItems) o;
      return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
      return Objects.hash(items);
    }

    @Override
    public String toString() {
      return "MultipleItems["
          + items.stream().map(PacketItem::toString).reduce((a, b) -> a + "," + b).orElse("")
          + "]";
    }
  }
}
