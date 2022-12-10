package it.twinsbrain.adc2022.day10;

import java.util.List;
import java.util.stream.Collectors;

public class Day10Solution {

    public static List<Instruction> parse(List<String> input) {
        return input.stream()
                .map(Day10Solution::rowToInstruction)
                .collect(Collectors.toList());
    }

    private static Instruction rowToInstruction(String s) {
        if (s.startsWith("add")) {
            return new Add(Integer.parseInt(s.substring(5)));
        } else {
            return NoOp.INSTANCE;
        }
    }

    sealed interface Instruction {
    }

    final static class NoOp implements Instruction {
        private NoOp() {
        }

        public static final NoOp INSTANCE = new NoOp();
    }

    record Add(int value) implements Instruction {
    }
}
