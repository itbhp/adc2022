package it.twinsbrain.adc2022.day07;

import static it.twinsbrain.adc2022.FilesModule.read;
import static it.twinsbrain.adc2022.FilesModule.resource;
import static it.twinsbrain.adc2022.day07.Day07Solution.parse;
import static it.twinsbrain.adc2022.day07.Day07Solution.part1;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.net.URISyntaxException;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class Day07SolutionTest {

  @Test
  void testParsing() {
    var input =
        List.of(
            "$ cd /",
            "$ ls",
            "dir a",
            "14848514 b.txt",
            "8504156 c.dat",
            "$ cd a",
            "$ ls",
            "29116 f",
            "2557 g");

    var parsed = parse(input);
    assertThat(
        parsed.toString(),
        equalTo("[/]->{b.txt(14848514),c.dat(8504156),[a]->{f(29116),g(2557)}}"));
  }

  @Test
  void dirSize() {
    var input =
        List.of(
            "$ cd /",
            "$ ls",
            "dir a",
            "14848514 b.txt",
            "8504156 c.dat",
            "$ cd a",
            "$ ls",
            "29116 f",
            "2557 g");

    var root = parse(input);
    assertThat(root.size(), equalTo(23384343));
  }

  @Nested
  class AcceptanceTest {
    private static final List<String> input;

    static {
      try {
        input = read(resource("/day07/input.txt"));
      } catch (URISyntaxException e) {
        throw new RuntimeException("input file not found!");
      }
    }

    @Test
    void part1Test() {
      assertThat(part1(input), is(1334506));
    }
  }
}
