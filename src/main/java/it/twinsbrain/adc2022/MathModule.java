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
        return elements.stream().reduce(1L, MathModule::lcm);
    }
}
