package it.twinsbrain.adc2022.day13;

import it.twinsbrain.adc2022.day13.Day13Solution.MultipleItems;
import it.twinsbrain.adc2022.day13.Day13Solution.Packet;
import it.twinsbrain.adc2022.day13.Day13Solution.PacketItem;
import it.twinsbrain.adc2022.day13.Day13Solution.SingleItem;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.twinsbrain.adc2022.FixtureModule.readSample;
import static it.twinsbrain.adc2022.day13.Day13Solution.parse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

class Day13SolutionTest {

    private static final List<String> sampleInput = readSample("day13");

    @Test
    void parse_not_nested_should_work() {
        Packet expected = new Packet(
                m(s(1), s(1), s(3), s(1), s(1)),
                m(s(1), s(1), s(5), s(1), s(1))
        );

        List<String> input = List.of(
                "[1,1,3,1,1]",
                "[1,1,5,1,1]",
                ""
        );
        assertThat(parse(input), equalTo(List.of(expected)));
    }

    @Test
    void parse_nested_should_work() {
        Packet expected = new Packet(
                m(m(s(1)), m(s(2), s(3), s(4))),
                m(m(s(1)), s(4))
        );

        List<String> input = List.of(
                "[[1],[2,3,4]]",
                "[[1],4]",
                ""
        );

        assertThat(parse(input), equalTo(List.of(expected)));
    }

    @Test
    void parse_sample_should_work() {
        Packet firstPacket = new Packet(
                m(s(1), s(1), s(3), s(1), s(1)),
                m(s(1), s(1), s(5), s(1), s(1))
        );
        Packet secondPacket = new Packet(
                m(m(s(1)), m(s(2), s(3), s(4))),
                m(m(s(1)), s(4))
        );
        Packet thirdPacket = new Packet(
                m(s(9)),
                m(m(s(8), s(7), s(6)))
        );
        Packet fourthPacket = new Packet(
                m(m(s(4), s(4)), s(4), s(4)),
                m(m(s(4), s(4)), s(4), s(4), s(4))
        );
        Packet fifthPacket = new Packet(
                m(s(7), s(7), s(7), s(7)),
                m(s(7), s(7), s(7))
        );
        Packet sixthPacket = new Packet(
                m(),
                m(s(3))
        );
        Packet seventhPacket = new Packet(
                m(m(m())),
                m(m())
        );
        Packet eightPacket = new Packet(
                m(s(1), m(s(2), m(s(3), m(s(4), m(s(5), s(6), s(7))))), s(8), s(9)),
                m(s(1), m(s(2), m(s(3), m(s(4), m(s(5), s(6), s(0))))), s(8), s(9))
        );
        assertThat(parse(sampleInput), contains(firstPacket,
                secondPacket,
                thirdPacket,
                fourthPacket,
                fifthPacket,
                sixthPacket,
                seventhPacket,
                eightPacket));
    }

    private static MultipleItems m(PacketItem... values) {
        return new MultipleItems(List.of(values));
    }

    private static SingleItem s(int value) {
        return new SingleItem(value);
    }
}