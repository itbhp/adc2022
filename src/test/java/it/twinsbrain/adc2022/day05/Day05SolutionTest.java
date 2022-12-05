package it.twinsbrain.adc2022.day05;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

        assertThat(Day05Solution.parseCrates(input), equalTo(expected));
    }
}