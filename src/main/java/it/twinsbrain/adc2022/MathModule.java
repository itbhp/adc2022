package it.twinsbrain.adc2022;

import java.util.List;

public class MathModule {

    public static long gcd(long a, long b) {
        if (b == 0)
            return a;
        else
            return gcd(b, a % b);
    }

    public static long lcm(long a, long b) {
        return (a * b) / gcd(a, b);
    }

    public static long lcm(List<Long> elements) {
        if (elements.size() <= 1) {
            return elements.stream().findFirst().orElse(1L);
        }
        long prev = elements.get(0);
        long res = prev;
        for (int i = 1; i < elements.size(); i++) {
            var elem = elements.get(i);
            res = lcm(prev, elem);
            prev = elem;
        }
        return res;
    }
}
