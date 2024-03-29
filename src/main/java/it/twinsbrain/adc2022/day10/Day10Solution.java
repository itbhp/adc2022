package it.twinsbrain.adc2022.day10;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day10Solution {

  public static int part1(List<String> input) {
    var cyclesForSignalStrength = List.of(20, 60, 100, 140, 180, 220);
    var instructions = parse(input);
    return signalStrength(cyclesForSignalStrength, instructions);
  }

  public static int signalStrength(List<Integer> onCycles, Queue<Instruction> instructions) {
    int lastCycle = onCycles.get(onCycles.size() - 1);
    var signalStrengthObserver = new SignalStrengthObserver(onCycles);
    var cpu = new Cpu(signalStrengthObserver);
    startProcessingUpToNCyclesCount(instructions, lastCycle, cpu);
    return signalStrengthObserver.strength();
  }

  public static void part2(List<String> input) {
    var instructions = parse(input);
    var observer = new DisplayObserver();
    var cpu = new Cpu(observer);
    startProcessingUpToNCyclesCount(instructions, 241, cpu);
  }

  private static void startProcessingUpToNCyclesCount(
      Queue<Instruction> instructions, int lastCycle, Cpu cpu) {
    IntStream.range(1, lastCycle + 1)
        .forEach(
            cycle -> {
              if (cpu.canProcessNextInstruction()) {
                cpu.execute(cycle, instructions.poll());
              } else {
                cpu.execute(cycle);
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

  sealed interface Instruction {}

  enum NoOp implements Instruction {
    INSTANCE
  }

  record Add(int value) implements Instruction {}

  static class Cpu {
    private final CpuObserver observer;
    private int registerX = 1;
    private Add currentInstruction;

    public Cpu(CpuObserver observer) {
      this.observer = observer;
    }

    public boolean canProcessNextInstruction() {
      return currentInstruction == null;
    }

    public void execute(int cycle, Instruction instruction) {
      if (instruction != null) {
        switch (instruction) {
          case Add i -> currentInstruction = i;
          case NoOp ignoredNoOp -> currentInstruction = null;
        }
      }
      observer.onExecute(cycle, registerX);
    }

    public void execute(int cycle) {
      observer.onExecute(cycle, registerX);
      if (currentInstruction != null) {
        registerX = registerX + currentInstruction.value;
        currentInstruction = null;
      }
    }
  }

  interface CpuObserver {
    void onExecute(int cycleCount, int registerValue);
  }

  static class DisplayObserver implements CpuObserver {
    private static final Integer DISPLAY_MAX_COLUMNS = 40;
    public static final int SPRITE_SIZE = 3;

    @Override
    public void onExecute(int cycleCount, int registerValue) {
      if ((cycleCount - 1) % DISPLAY_MAX_COLUMNS == 0) {
        System.out.print("\n");
      }
      int cursorPos = cycleCount % DISPLAY_MAX_COLUMNS;
      if (cursorInSpriteRange(registerValue, cursorPos)) {
        System.out.print("█");
      } else {
        System.out.print(" ");
      }
    }

    private static boolean cursorInSpriteRange(int registerValue, int cursorPos) {
      return cursorPos >= registerValue && cursorPos < registerValue + SPRITE_SIZE;
    }
  }

  static class SignalStrengthObserver implements CpuObserver {
    private final AtomicInteger strength = new AtomicInteger(0);
    private final List<Integer> cyclesToObserve;

    SignalStrengthObserver(List<Integer> cyclesToObserve) {
      this.cyclesToObserve = cyclesToObserve;
    }

    @Override
    public void onExecute(int cycleCount, int registerValue) {
      if (cyclesToObserve.contains(cycleCount)) {
        strength.addAndGet(cycleCount * registerValue);
      }
    }

    public int strength() {
      return strength.intValue();
    }
  }
}
