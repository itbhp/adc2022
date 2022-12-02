package it.twinsbrain.adc2022.day01;

import it.twinsbrain.adc2022.FilesModule;

import java.net.URISyntaxException;
import java.util.List;

import static it.twinsbrain.adc2022.FilesModule.read;
import static it.twinsbrain.adc2022.FilesModule.resource;

public class Day01Solution {

    public static void main(String[] args) throws URISyntaxException {
        var input = read(resource("/day01/input01.txt"));
        System.out.printf("Max calories: %d", maxCalories(input));
    }


    public static int maxCalories(List<String> input) {
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
}
