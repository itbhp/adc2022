package it.twinsbrain.adc2022.day06;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day06Solution {

    public static int endPositionFirstMarker(List<String> input) {
        String buffer = input.get(0);
        var window = new Window(4);
        AtomicInteger index = new AtomicInteger(1);
        buffer.chars()
                .mapToObj(i -> (char) i)
                .takeWhile(c -> window.markerWasNotFound())
                .forEach(c -> {
                    window.accept(c, index.intValue());
                    index.getAndIncrement();
                });
        return window.lastIndex;
    }

    static class Window {
        private final LinkedList<Character> queue = new LinkedList<>();
        private final int size;
        private int lastIndex = -1;

        Window(int size) {
            this.size = size;
        }

        public void accept(Character c, int index) {
            if (queue.contains(c)) {
                while (queue.removeFirst() != c) {
                }
            }
            queue.add(c);
            lastIndex = index;
        }

        public boolean markerWasNotFound() {
            return queue.size() != size;
        }
    }
}
