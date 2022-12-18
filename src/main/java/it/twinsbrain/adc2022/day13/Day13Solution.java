package it.twinsbrain.adc2022.day13;

import static it.twinsbrain.adc2022.GroupingModule.chunked;
import static java.util.stream.Collectors.toList;

import java.util.List;

public class Day13Solution {

    public static List<Packet> parse(List<String> input) {
        return chunked(input, 3).stream().map(Day13Solution::toPacket).collect(toList());
    }

    private static Packet toPacket(List<String> strings) {
        throw new UnsupportedOperationException();
    }


    record Packet(MultipleItems left, MultipleItems right) {
    }

    sealed interface PacketItem {
    }

    record SingleItem(int value) implements PacketItem {
    }

    record MultipleItems(List<PacketItem> values) implements PacketItem {
    }
}
