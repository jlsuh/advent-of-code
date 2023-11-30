package day03;

import utils.InputReader;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class Solution {

    public static void main(String[] args) {
        BufferedReader br = InputReader.getBufferedReader("day03/input.txt");
        List<String> lines = InputReader.getBufferedReaderLines(br);
        System.out.println(part1Solution(lines));
        System.out.println(part2Solution(lines));
    }

    private static Integer toPriority(char c) {
        int factor = Character.isLowerCase(c) ? 96 : 38;
        return c - factor;
    }

    private static Integer sumPriorities(List<String> items) {
        return items.stream().map(s -> toPriority(s.charAt(0))).reduce(0, Integer::sum);
    }

    private static Integer part1Solution(List<String> lines) {
        List<String> bothContained = new ArrayList<>();
        lines.forEach(l -> {
            int secondHalfLength = l.length() / 2;
            String firstHalf = l.substring(0, secondHalfLength);
            String secondHalf = l.substring(secondHalfLength);
            for (int i = 0; i < secondHalf.length(); i += 1) {
                String curr = String.valueOf(firstHalf.charAt(i));
                if (secondHalf.contains(curr)) {
                    bothContained.add(curr);
                    break;
                }
            }
        });
        return sumPriorities(bothContained);
    }

    private static Integer part2Solution(List<String> lines) {
        List<String> badges = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += 3) {
            String first = lines.get(i);
            String second = lines.get(i + 1);
            String third = lines.get(i + 2);
            for (int j = 0; j < first.length(); j += 1) {
                String curr = String.valueOf(first.charAt(j));
                if (second.contains(curr) && third.contains(curr)) {
                    badges.add(curr);
                    break;
                }
            }
        }
        return sumPriorities(badges);
    }

}
