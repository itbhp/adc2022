package it.twinsbrain.adc2022.day13;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import static it.twinsbrain.adc2022.GroupingModule.chunked;
import static java.util.stream.Collectors.toList;

public class Day13Solution {

    public static List<Packet> parse(List<String> input) {
        return chunked(input, 3).stream().map(Day13Solution::toPacket).collect(toList());
    }

    private static Packet toPacket(List<String> strings) {
        var left = strings.get(0);
        var right = strings.get(1);
        Packet packet = new Packet(toItems(left), toItems(right));
        return packet;
    }

    private static MultipleItems toItems(String source) {
        var chars = source;
        var items = new ArrayList<PacketItem>();
        PacketItem current = null;
        var stack = new Stack<Character>();
        for (int i = 0; i < chars.length(); i++) {
            String c = chars.substring(i, i + 1);
            switch (c) {
                case "[" -> stack.add('[');
                case "]" -> stack.pop();
                case "," -> {
                    continue;
                }
                default -> {
                    if (current == null) {
                        current = new SingleItem(Integer.parseInt(c));
                    } else {
                        switch (current) {
                            case MultipleItems multipleItems -> {
                                multipleItems.addItem(new SingleItem(Integer.parseInt(c)));
                            }
                            case SingleItem singleItem -> {
                                var newCurrent = new MultipleItems();
                                newCurrent.addItem(singleItem);
                                newCurrent.addItem(new SingleItem(Integer.parseInt(c)));
                                current = newCurrent;
                            }
                        }
                    }
                }
            }
            if (stack.isEmpty()) {
                items.add(current);
                current = null;
            }
        }
        return new MultipleItems(items);
    }


    record Packet(MultipleItems left, MultipleItems right) {
    }

    sealed interface PacketItem {
    }

    record SingleItem(int value) implements PacketItem {
    }

    static final class MultipleItems implements PacketItem {
        private List<PacketItem> items;

        public MultipleItems() {
            this(new ArrayList<>());
        }

        public MultipleItems(List<PacketItem> items) {
            this.items = items;
        }

        public void addItem(PacketItem item) {
            items.add(item);
        }

        public List<PacketItem> getItems() {
            return items;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MultipleItems that = (MultipleItems) o;
            return Objects.equals(items, that.items);
        }

        @Override
        public int hashCode() {
            return Objects.hash(items);
        }

        @Override
        public String toString() {
            return "MultipleItems[" +
                    items.stream()
                            .map(PacketItem::toString)
                            .reduce((a, b) -> a + "," + b)
                            .orElse("")
                    + "]";
        }
    }
}
