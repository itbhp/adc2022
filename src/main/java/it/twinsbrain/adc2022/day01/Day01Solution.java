package it.twinsbrain.adc2022.day01;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static it.twinsbrain.adc2022.FilesModule.read;
import static it.twinsbrain.adc2022.FilesModule.resource;

public class Day01Solution {

    public static void main(String[] args) throws URISyntaxException {
        var input = read(resource("/day01/input01.txt"));
        System.out.printf("Max calories: %d", maxCaloriesByOneElf(input));
        System.out.println();
        System.out.printf("Max calories top 3 elves: %d", maxCaloriesByThreeElves(input));
    }

    public static int maxCaloriesByThreeElves(List<String> input) {
        return caloriesByElf(input)
                .sorted(Collections.reverseOrder())
                .limit(3)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public static int maxCaloriesByOneElf(List<String> input) {
        var sum = 0;
        var maxSum = 0;
        for (String item : input) {
            if (item.length() > 0) {
                sum += Integer.parseInt(item);
            } else {
                if (sum > maxSum) {
                    maxSum = sum;
                }
                sum = 0;
            }
        }
        return maxSum;
    }

    private static int singleElfCalories(String s) {
        return Arrays.stream(s.split("\n")).mapToInt(Integer::valueOf).sum();
    }

    private static Stream<Integer> caloriesByElf(List<String> input) {
        String[] caloriesByElf = String.join("\n", input).split("\n\n");
        return Arrays.stream(caloriesByElf)
                .mapToInt(Day01Solution::singleElfCalories)
                .boxed();
    }
}
