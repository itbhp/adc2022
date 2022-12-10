package it.twinsbrain.adc2022.day10;

import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static it.twinsbrain.adc2022.FilesModule.read;
import static it.twinsbrain.adc2022.FilesModule.resource;

public class Day10Solution {

    public static void main(String[] args) throws URISyntaxException {
        var input = read(resource("/day10/input.txt"));
        System.out.printf("Part 1: %s", part1(input));
        System.out.println();
        System.out.println("Part 2");
        part2(input); // PLPAFBCL
    }


    public static int part1(List<String> input) {
        var cyclesForSignalStrength = List.of(20, 60, 100, 140, 180, 220);
        Queue<Instruction> instructions = parse(input);
        return signalStrength(cyclesForSignalStrength, instructions);
    }

    public static int signalStrength(List<Integer> onCycles, Queue<Instruction> instructions) {
        int lastCycle = onCycles.get(onCycles.size() - 1);

        var signalStrengthObserver = new SignalStrengthObserver(onCycles);
        var cpu = new Cpu(signalStrengthObserver);
        IntStream.range(1, lastCycle + 1).forEach(cycle -> {
            if (cpu.canProcessNextInstruction()) {
                cpu.process(cycle, instructions.poll());
            } else {
                cpu.process(cycle);
            }
        });
        return signalStrengthObserver.strength();
    }

    public static void part2(List<String> input) {
        Queue<Instruction> instructions = parse(input);
        var observer = new DisplayObserver();
        var cpu = new Cpu(observer);
        IntStream.range(1, 241).forEach(cycle -> {
            if (cpu.canProcessNextInstruction()) {
                cpu.process(cycle, instructions.poll());
            } else {
                cpu.process(cycle);
            }
        });
    }

    public static LinkedList<Instruction> parse(List<String> input) {
        return input.stream()
                .map(Day10Solution::rowToInstruction)
                .collect(Collectors.toCollection(LinkedList::new));
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

    enum NoOp implements Instruction {
        INSTANCE
    }

    record Add(int value) implements Instruction {
    }

    static class Cpu {
        private final CpuObserver observer;
        private int registerX = 1;
        private Optional<Instruction> currentInstruction = Optional.empty();
        private boolean pendingInstruction = false;

        public Cpu(CpuObserver observer) {
            this.observer = observer;
        }

        public boolean canProcessNextInstruction() {
            return !pendingInstruction;
        }

        public void process(int cycle, Instruction instruction) {
            currentInstruction = Optional.ofNullable(instruction);
            currentInstruction.ifPresent(i -> {
                switch (i) {
                    case Add ignored -> pendingInstruction = true;
                    case NoOp ignoredNoOp -> pendingInstruction = false;
                }
            });
            if (!pendingInstruction) {
                currentInstruction = Optional.empty();
            }
            observer.onCycle(cycle, registerX);
        }

        public void process(int cycle) {
            observer.onCycle(cycle, registerX);
            currentInstruction.ifPresent(current -> {
                registerX = registerX + ((Add) current).value;
                pendingInstruction = false;
            });
            if (!pendingInstruction) {
                currentInstruction = Optional.empty();
            }
        }
    }

    @FunctionalInterface
    interface CpuObserver {
        void onCycle(int cycleCount, int registerValue);
    }


    static class DisplayObserver implements CpuObserver {
        private static final Integer SIZE = 40;

        @Override
        public void onCycle(int cycleCount, int registerValue) {
            if ((cycleCount - 1) % SIZE == 0) {
                System.out.print("\n");
            }
            int cursorPos = cycleCount % SIZE;
            if (cursorInSpriteRange(registerValue, cursorPos)) {
                System.out.print("#");
            } else {
                System.out.print(".");
            }

        }

        private static boolean cursorInSpriteRange(int registerValue, int cursorPos) {
            Set<Integer> range = IntStream.range(registerValue, registerValue + 3)
                    .mapToObj(Integer::valueOf)
                    .collect(Collectors.toSet());
            return range.contains(cursorPos);
        }
    }

    static class SignalStrengthObserver implements CpuObserver {
        private final AtomicInteger strength = new AtomicInteger(0);
        private final List<Integer> cyclesToObserve;

        SignalStrengthObserver(List<Integer> cyclesToObserve) {
            this.cyclesToObserve = cyclesToObserve;
        }

        @Override
        public void onCycle(int cycleCount, int registerValue) {
            if (cyclesToObserve.contains(cycleCount)) {
                strength.addAndGet(cycleCount * registerValue);
            }
        }

        public int strength() {
            return strength.intValue();
        }
    }
}