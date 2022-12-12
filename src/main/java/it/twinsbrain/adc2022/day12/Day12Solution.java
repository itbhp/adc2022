package it.twinsbrain.adc2022.day12;

import java.util.List;

public class Day12Solution {

    public static char[][] parse(List<String> input) {
        var rows = input.size();
        char[][] res = new char[rows][];
        for (int j = 0; j < input.size(); j++) {
            String line = input.get(j);
            char[] row = new char[line.length()];
            String[] split = line.split("");
            for (int i = 0; i < split.length; i++) {
                row[i] = split[i].toCharArray()[0];
            }
            res[j] = row;
        }
        return res;
    }
}
