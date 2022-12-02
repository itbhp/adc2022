package it.twinsbrain.adc2022.day01;

import java.util.List;

public class Day01Solution {
    public static int maxCalories(List<String> input){
        return input.stream().mapToInt(Integer::parseInt).sum();
    }
}
