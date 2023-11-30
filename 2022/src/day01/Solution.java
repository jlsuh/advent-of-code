package day01;

import utils.InputReader;

import java.io.*;
import java.util.List;

public class Solution {

    public static void main(String[] args) {
        solution();
    }

    private static void solution() {
        BufferedReader br = InputReader.getBufferedReader("day01/input.txt");
        List<String> lines = InputReader.getBufferedReaderLines(br);
        CaloriesTracker caloriesTracker = new CaloriesTracker(lines);
        // Part 1
        Elf elfWithMostCalories = caloriesTracker.getElfWithMostCalories();
        System.out.println(elfWithMostCalories.getCaloriesSum().toString());
        // Part 2
        Integer totalCaloriesTop3Elves = caloriesTracker.getTotalCaloriesTop3Elves();
        System.out.println(totalCaloriesTop3Elves);
    }

}
