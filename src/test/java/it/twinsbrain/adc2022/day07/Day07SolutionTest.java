package it.twinsbrain.adc2022.day07;

import org.junit.jupiter.api.Test;

import java.util.List;

import static it.twinsbrain.adc2022.day07.Day07Solution.parse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class Day07SolutionTest {

    @Test
    void testParsing() {
        List<String> input = List.of(
                "$ cd /",
                "$ ls",
                "dir a",
                "14848514 b.txt",
                "8504156 c.dat",
                "$ cd a",
                "$ ls",
                "29116 f",
                "2557 g"
        );

        var parsed = parse(input);
        assertThat(parsed.toString(), equalTo("[/]->{b.txt(14848514),c.dat(8504156),[a]->{f(29116),g(2557)}}"));
    }
}