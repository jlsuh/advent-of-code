package day08;

import utils.InputReader;

import java.io.BufferedReader;
import java.util.*;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) {
        BufferedReader br = InputReader.getBufferedReader("day08/input.txt");
        List<String> lines = InputReader.getBufferedReaderLines(br);
        System.out.println(part1Solution(lines));
        System.out.println(part2Solution(lines));
    }

    private static Boolean isAllShorterTrees(Set<String> trees, Character targetHeight) {
        return trees.stream().allMatch(s -> s.charAt(0) < targetHeight);
    }

    private static Boolean isHorizontallyVisible(String row, Integer pivot) {
        char targetHeight = row.charAt(pivot);
        Set<String> before = new HashSet<>(Arrays.asList(row.substring(0, pivot).split("")));
        if (isAllShorterTrees(before, targetHeight)) return true;
        return new HashSet<>(Arrays.asList(row.substring(pivot + 1).split(""))).stream().allMatch(s -> s.charAt(0) < targetHeight);
    }

    private static Boolean isVerticallyVisible(List<String> lines, Integer pivotCol, Integer pivotRow) {
        char targetHeight = lines.get(pivotRow).charAt(pivotCol);
        Set<String> before = new HashSet<>();
        for (int i = 0; i < pivotRow; i += 1) before.add(String.valueOf(lines.get(i).charAt(pivotCol)));
        if (isAllShorterTrees(before, targetHeight)) return true;
        Set<String> after = new HashSet<>();
        for (int i = pivotRow + 1; i < lines.size(); i += 1) after.add(String.valueOf(lines.get(i).charAt(pivotCol)));
        return isAllShorterTrees(after, targetHeight);
    }

    private static Integer part1Solution(List<String> lines) {
        int visibleTrees = 2 * (lines.size() + lines.get(0).length() - 4) + 4;
        for (int i = 1; i < lines.size() - 1; i += 1) {
            String line = lines.get(i);
            for (int j = 1; j < line.length() - 1; j += 1)
                if (isHorizontallyVisible(line, j) || isVerticallyVisible(lines, j, i)) visibleTrees += 1;
        }
        return visibleTrees;
    }

    private static Integer getScore(List<String> trees, Character targetHeight) {
        int score = 0;
        if (trees.size() == 0) return 1;
        for (String s : trees) {
            score += 1;
            char tree = s.charAt(0);
            if (tree >= targetHeight) break;
        }
        return score;
    }

    private static Integer part2Solution(List<String> lines) {
        int highestScore = 0;
        for (int i = 1; i < lines.size() - 1; i += 1) {
            String line = lines.get(i);
            for (int j = 1; j < lines.get(i).length() - 1; j += 1) {
                int pivotCol = j;
                List<String> left = Arrays.asList(line.substring(0, pivotCol).split(""));
                List<String> right = Arrays.asList(line.substring(pivotCol + 1).split(""));
                List<String> up = lines.subList(0, i).stream().map(s -> String.valueOf(s.charAt(pivotCol))).collect(Collectors.toList());
                List<String> down = lines.subList(i + 1, lines.size()).stream().map(s -> String.valueOf(s.charAt(pivotCol))).collect(Collectors.toList());
                Collections.reverse(left);
                Collections.reverse(up);
                Integer leftScore = getScore(left, line.charAt(pivotCol));
                Integer rightScore = getScore(right, line.charAt(pivotCol));
                Integer upScore = getScore(up, line.charAt(pivotCol));
                Integer downScore = getScore(down, line.charAt(pivotCol));
                highestScore = Math.max(highestScore, leftScore * rightScore * upScore * downScore);
            }
        }
        return highestScore;
    }

}
