package it.twinsbrain.adc2022.day07;

import org.junit.jupiter.api.Test;

import java.util.List;

import static it.twinsbrain.adc2022.day07.Day07Solution.parse;

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

        Directory expected = Directory.root();
        Directory a = new Directory(
                "a",
                List.of(
                        new File("f", 29116),
                        new File("g", 2557)
                ),
                expected
        );
        expected.addSubDir(a);
        expected.addFile("b.txt", 14848514);
        expected.addFile("c.dat", 8504156);

        var parse = parse(input);
        System.out.println("parsed");
    }
}