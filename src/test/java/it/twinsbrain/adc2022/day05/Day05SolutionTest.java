package it.twinsbrain.adc2022.day05;

import it.twinsbrain.adc2022.day05.Day05Solution.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static it.twinsbrain.adc2022.FilesModule.read;
import static it.twinsbrain.adc2022.FilesModule.resource;
import static it.twinsbrain.adc2022.day05.Day05Solution.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class Day05SolutionTest {

    @Test
    void parseCratesStacks() {
        List<String> input = List.of(
                "        [H]     [W] [B]           ",
                "    [D] [B]     [L] [G] [N]       ",
                "[P] [J] [T]     [M] [R] [D]       ",
                "[V] [F] [V]     [F] [Z] [B]     [C]",
                "[Z] [V] [S]     [G] [H] [C] [Q] [R]",
                "[W] [W] [L] [J] [B] [V] [P] [B] [Z]",
                "[D] [S] [M] [S] [Z] [W] [J] [T] [G]",
                "[T] [L] [Z] [R] [C] [Q] [V] [P] [H]",
                " 1   2   3   4   5   6   7   8   9"
        );

        var expected = new ArrayList<LinkedList<String>>(9);
        expected.add(new LinkedList<>() {{
            add("P");
            add("V");
            add("Z");
            add("W");
            add("D");
            add("T");
        }});

        expected.add(new LinkedList<>() {{
            add("D");
            add("J");
            add("F");
            add("V");
            add("W");
            add("S");
            add("L");
        }});

        expected.add(new LinkedList<>() {{
            add("H");
            add("B");
            add("T");
            add("V");
            add("S");
            add("L");
            add("M");
            add("Z");
        }});

        expected.add(new LinkedList<>() {{
            add("J");
            add("S");
            add("R");
        }});

        expected.add(new LinkedList<>() {{
            add("W");
            add("L");
            add("M");
            add("F");
            add("G");
            add("B");
            add("Z");
            add("C");
        }});

        expected.add(new LinkedList<>() {{
            add("B");
            add("G");
            add("R");
            add("Z");
            add("H");
            add("V");
            add("W");
            add("Q");
        }});

        expected.add(new LinkedList<>() {{
            add("N");
            add("D");
            add("B");
            add("C");
            add("P");
            add("J");
            add("V");
        }});

        expected.add(new LinkedList<>() {{
            add("Q");
            add("B");
            add("T");
            add("P");
        }});

        expected.add(new LinkedList<>() {{
            add("C");
            add("R");
            add("Z");
            add("G");
            add("H");
        }});

        assertThat(parseCrates(input), equalTo(expected));
    }

    @Test
    void parseCommands() {
        List<String> input = List.of(
                "        [H]     [W] [B]           ",
                "    [D] [B]     [L] [G] [N]       ",
                "[P] [J] [T]     [M] [R] [D]       ",
                "[V] [F] [V]     [F] [Z] [B]     [C]",
                "[Z] [V] [S]     [G] [H] [C] [Q] [R]",
                "[W] [W] [L] [J] [B] [V] [P] [B] [Z]",
                "[D] [S] [M] [S] [Z] [W] [J] [T] [G]",
                "[T] [L] [Z] [R] [C] [Q] [V] [P] [H]",
                " 1   2   3   4   5   6   7   8   9",
                "                                  ",
                "move 3 from 2 to 9",
                "move 1 from 1 to 6",
                "move 6 from 6 to 7"
        );

        var expected = List.of(
                new Command(3, 2, 9),
                new Command(1, 1, 6),
                new Command(6, 6, 7)
        );

        assertThat(Day05Solution.parseCommands(input), equalTo(expected));
    }

    @Test
    void part1ShouldWork() {
        List<String> input = List.of(
                "        [H]     [W] [B]           ",
                "    [D] [B]     [L] [G] [N]       ",
                "[P] [J] [T]     [M] [R] [D]       ",
                "[V] [F] [V]     [F] [Z] [B]     [C]",
                "[Z] [V] [S]     [G] [H] [C] [Q] [R]",
                "[W] [W] [L] [J] [B] [V] [P] [B] [Z]",
                "[D] [S] [M] [S] [Z] [W] [J] [T] [G]",
                "[T] [L] [Z] [R] [C] [Q] [V] [P] [H]",
                " 1   2   3   4   5   6   7   8   9",
                "                                  ",
                "move 2 from 2 to 9",
                "move 1 from 1 to 6"
        );

        var expected = "VFHJWPNQJ";

        assertThat(part1(input), equalTo(expected));
    }

    @Nested
    class AcceptanceTest {
        private static final List<String> input;

        static {
            try {
                input = read(resource("/day05/input.txt"));
            } catch (URISyntaxException e) {
                throw new RuntimeException("input file not found!");
            }
        }

        @Test
        void part1Test() {
            assertThat(part1(input), equalTo("TLFGBZHCN"));
        }

        @Test
        void part2Test() {
            assertThat(part2(input), equalTo("QRQFHFWCL"));
        }
    }
}