package day14;

import utils.InputReader;

import java.io.BufferedReader;
import java.util.AbstractMap.SimpleEntry;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class Solution {

    private final static String NUMBER = "\\d+";
    private final static Character ROCK = '#';
    private final static Character SAND = 'o';
    private final static Character AIR = '.';
    private final static Character SAND_ORIGIN = '+';
    private static SimpleEntry<Integer, Integer> sandSrc = new SimpleEntry<>(500, 0);

    public static void main(String[] args) {
        BufferedReader br = InputReader.getBufferedReader("day14/input.txt");
        List<String> input = InputReader.getBufferedReaderLines(br);
        System.out.println(part1Solution(input));
        System.out.println();
        System.out.println(part2Solution(input));
    }

    private static void printCave(List<List<Character>> cave) {
        for (List<Character> row : cave) {
            for (Character c : row) System.out.print(c);
            System.out.println();
        }
    }

    private static List<List<SimpleImmutableEntry<Integer, Integer>>> extractPaths(List<String> input) {
        List<List<SimpleImmutableEntry<Integer, Integer>>> paths = new ArrayList<>();
        for (String line : input) {
            List<SimpleImmutableEntry<Integer, Integer>> path = new ArrayList<>();
            Scanner scanner = new Scanner(line);
            String horizontal = scanner.findInLine(NUMBER);
            String vertical = scanner.findInLine(NUMBER);
            SimpleImmutableEntry<Integer, Integer> lastPair = new SimpleImmutableEntry<>(Integer.parseInt(horizontal), Integer.parseInt(vertical));
            path.add(lastPair);
            while (scanner.hasNext()) {
                horizontal = scanner.findInLine(NUMBER);
                vertical = scanner.findInLine(NUMBER);
                SimpleImmutableEntry<Integer, Integer> currPair = new SimpleImmutableEntry<>(Integer.parseInt(horizontal), Integer.parseInt(vertical));
                if (currPair.getKey() - lastPair.getKey() > 1) {
                    for (int i = lastPair.getKey() + 1; i < currPair.getKey(); i += 1)
                        path.add(new SimpleImmutableEntry<>(i, lastPair.getValue()));
                } else if (currPair.getKey() - lastPair.getKey() < -1) {
                    for (int i = lastPair.getKey() - 1; i > currPair.getKey(); i -= 1)
                        path.add(new SimpleImmutableEntry<>(i, lastPair.getValue()));
                } else if (currPair.getValue() - lastPair.getValue() > 1) {
                    for (int i = lastPair.getValue() + 1; i < currPair.getValue(); i += 1)
                        path.add(new SimpleImmutableEntry<>(lastPair.getKey(), i));
                } else if (currPair.getValue() - lastPair.getValue() < -1) {
                    for (int i = lastPair.getValue() - 1; i > currPair.getValue(); i -= 1)
                        path.add(new SimpleImmutableEntry<>(lastPair.getKey(), i));
                }
                lastPair = currPair;
                path.add(currPair);
            }
            paths.add(path);
            scanner.close();
        }
        return paths;
    }

    private static List<List<Character>> traceCave(List<List<SimpleImmutableEntry<Integer, Integer>>> paths, Integer horizontalMin, Integer horizontalMax, Integer verticalMax) {
        List<List<Character>> cave = new ArrayList<>();
        for (int i = 0; i < verticalMax + 1; i += 1) {
            List<Character> row = new ArrayList<>();
            for (int j = 0; j < horizontalMax - horizontalMin + 1; j += 1) row.add(AIR);
            cave.add(row);
        }
        cave.get(sandSrc.getValue()).set(sandSrc.getKey() - horizontalMin, SAND_ORIGIN);
        for (List<SimpleImmutableEntry<Integer, Integer>> path : paths) {
            for (SimpleImmutableEntry<Integer, Integer> pair : path)
                cave.get(pair.getValue()).set(pair.getKey() - horizontalMin, ROCK);
        }
        return cave;
    }

    private static Boolean isOutOfBoundaries(List<List<Character>> cave, Integer currX, Integer currY) {
        return currX + 1 == cave.get(0).size() || currX - 1 == -1 || currY + 1 == cave.size();
    }

    private static SimpleImmutableEntry<Integer, Integer> transformSandPosition(List<List<Character>> cave, Integer currX, Integer currY) {
        Function<Character, Boolean> isBlockedTile = tile -> tile == ROCK || tile == SAND;
        Character down = cave.get(currY + 1).get(currX);
        Character downLeft = cave.get(currY + 1).get(currX - 1);
        Character downRight = cave.get(currY + 1).get(currX + 1);
        return !isBlockedTile.apply(down) ? new SimpleImmutableEntry<>(0, 1)
                : !isBlockedTile.apply(downLeft) ? new SimpleImmutableEntry<>(-1, 1)
                : !isBlockedTile.apply(downRight) ? new SimpleImmutableEntry<>(1, 1)
                : new SimpleImmutableEntry<>(0, 0);
    }

    private static Boolean isSamePositionAsBefore(Integer x, Integer y) {
        return x == 0 && y == 0;
    }

    private static Integer simulateFallingSand(List<List<Character>> cave, Integer horizontalMin) {
        int sandsAtRest = 0;
        for (; ; ) {
            int currX = sandSrc.getKey() - horizontalMin;
            int currY = sandSrc.getValue();
            for (; ; ) {
                if (isOutOfBoundaries(cave, currX, currY)) {
                    printCave(cave);
                    return sandsAtRest;
                }
                SimpleImmutableEntry<Integer, Integer> newSandPos = transformSandPosition(cave, currX, currY);
                if (isSamePositionAsBefore(newSandPos.getKey(), newSandPos.getValue())) break;
                currX += newSandPos.getKey();
                currY += newSandPos.getValue();
            }
            cave.get(currY).set(currX, SAND);
            sandsAtRest += 1;
        }
    }

    private static Integer[] caveLimits(List<List<SimpleImmutableEntry<Integer, Integer>>> paths) {
        int horizontalMin = Integer.MAX_VALUE;
        int horizontalMax = 0;
        int verticalMax = 0;
        for (List<SimpleImmutableEntry<Integer, Integer>> path : paths) {
            horizontalMin = Math.min(horizontalMin, path.stream().min(Map.Entry.comparingByKey()).orElse(new SimpleImmutableEntry<>(0, 0)).getKey());
            horizontalMax = Math.max(horizontalMax, path.stream().max(Map.Entry.comparingByKey()).orElse(new SimpleImmutableEntry<>(0, 0)).getKey());
            verticalMax = Math.max(verticalMax, path.stream().max(Map.Entry.comparingByValue()).orElse(new SimpleImmutableEntry<>(0, 0)).getValue());
        }
        return new Integer[]{horizontalMin, horizontalMax, verticalMax};
    }

    private static Integer part1Solution(List<String> input) {
        List<List<SimpleImmutableEntry<Integer, Integer>>> paths = extractPaths(input);
        Integer[] caveLimits = caveLimits(paths);
        List<List<Character>> cave = traceCave(paths, caveLimits[0], caveLimits[1], caveLimits[2]);
        return simulateFallingSand(cave, caveLimits[0]);
    }

    private static Boolean isBlockedSandSrc(List<List<Character>> cave, Integer horizontalMin) {
        return cave.get(sandSrc.getValue()).get(sandSrc.getKey() - horizontalMin) == SAND;
    }

    private static Integer simulateFallingSandWithInfiniteHorizontalPlatform(List<List<Character>> cave, Integer horizontalMin) {
        int sandsAtRest = 0;
        for (; ; ) {
            int currX = sandSrc.getKey() - horizontalMin;
            int currY = sandSrc.getValue();
            for (; ; ) {
                if (isBlockedSandSrc(cave, horizontalMin)) {
                    printCave(cave);
                    return sandsAtRest;
                }
                if (currX + 1 == cave.get(0).size()) {
                    for (int i = 0; i < cave.size(); i += 1) {
                        if (i == cave.size() - 1) cave.get(i).add(ROCK);
                        else cave.get(i).add(AIR);
                    }
                } else if (currX - 1 == -1) {
                    for (int i = 0; i < cave.size(); i += 1) {
                        if (i == cave.size() - 1) cave.get(i).add(0, ROCK);
                        else cave.get(i).add(0, AIR);
                    }
                    currX += 1;
                    sandSrc = new SimpleEntry<>(sandSrc.getKey() + 1, sandSrc.getValue());
                }
                SimpleImmutableEntry<Integer, Integer> newSandPos = transformSandPosition(cave, currX, currY);
                if (isSamePositionAsBefore(newSandPos.getKey(), newSandPos.getValue())) break;
                currX += newSandPos.getKey();
                currY += newSandPos.getValue();
            }
            cave.get(currY).set(currX, SAND);
            sandsAtRest += 1;
        }
    }

    private static Integer part2Solution(List<String> input) {
        List<List<SimpleImmutableEntry<Integer, Integer>>> paths = extractPaths(input);
        Integer[] caveLimits = caveLimits(paths);
        int infinitePlatformVerticalIndex = caveLimits[2] + 2;
        List<List<Character>> cave = traceCave(paths, caveLimits[0], caveLimits[1], infinitePlatformVerticalIndex);
        for (int i = 0; i < cave.get(0).size(); i += 1) cave.get(infinitePlatformVerticalIndex).set(i, ROCK);
        return simulateFallingSandWithInfiniteHorizontalPlatform(cave, caveLimits[0]);
    }

}
