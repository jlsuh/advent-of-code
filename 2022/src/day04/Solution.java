package day04;

import utils.InputReader;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Solution {

    public static void main(String[] args) {
        BufferedReader br = InputReader.getBufferedReader("day04/input.txt");
        List<String> lines = InputReader.getBufferedReaderLines(br);
        System.out.println(part1Solution(lines));
        System.out.println(part2Solution(lines));
    }

    private static int[] getSectionLimits(String ln) {
        String[] pair = ln.split(",");
        int[] firstElf = Arrays.stream(pair[0].split("-")).mapToInt(Integer::parseInt).toArray();
        int[] secondElf = Arrays.stream(pair[1].split("-")).mapToInt(Integer::parseInt).toArray();
        return new int[]{firstElf[0], firstElf[1], secondElf[0], secondElf[1]};
    }

    private static Integer part1Solution(List<String> lines) {
        final int[] fullyContains = {0};
        lines.forEach(ln -> {
            int[] sections = getSectionLimits(ln);
            int points = (sections[0] <= sections[2] && sections[1] >= sections[3] || sections[2] <= sections[0] && sections[3] >= sections[1]) ? 1 : 0;
            fullyContains[0] += points;
        });
        return fullyContains[0];
    }

    private static Integer part2Solution(List<String> lines) {
        final int[] overlapping = {0};
        lines.forEach(ln -> {
            int[] sections = getSectionLimits(ln);
            List<Integer> firstElfSections = IntStream.rangeClosed(sections[0], sections[1]).boxed().toList();
            List<Integer> secondElfSections = IntStream.rangeClosed(sections[2], sections[3]).boxed().toList();
            overlapping[0] += firstElfSections.stream().anyMatch(secondElfSections::contains) ? 1 : 0;
        });
        return overlapping[0];
    }

}
