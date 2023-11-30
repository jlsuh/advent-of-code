package day10;

import utils.InputReader;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        BufferedReader br = InputReader.getBufferedReader("day10/input.txt");
        List<String> input = InputReader.getBufferedReaderLines(br);
        List<Integer> wantedCycles = Arrays.asList(20, 60, 100, 140, 180, 220);
        solutions(input, wantedCycles);
    }

    private static Integer getSignalStrength(List<Integer> wantedCycles, int x, int cycle) {
        return wantedCycles.contains(cycle) ? cycle * x : 0;
    }

    private static List<Integer> getSpritePosition(int center) {
        return Arrays.asList(center - 1, center, center + 1);
    }

    private static void drawCrt(char[][] crt, Integer x, Integer cycles) {
        int crtColIndex = (cycles - 1) % crt[0].length;
        int crtRowIndex = (cycles - 1) / crt[0].length;
        crt[crtRowIndex][crtColIndex] = getSpritePosition(x).contains(crtColIndex) ? '#' : '.';
    }

    private static void solutions(List<String> program, List<Integer> wantedCycles) {
        char[][] crt = new char[6][40];
        int totalSum = 0;
        int cycles = 1;
        int x = 1;
        for (String line : program) {
            Scanner s = new Scanner(line);
            s.next();
            drawCrt(crt, x, cycles);
            cycles += 1;
            if (s.hasNextInt()) {
                totalSum += getSignalStrength(wantedCycles, x, cycles);
                drawCrt(crt, x, cycles);
                cycles += 1;
                x += s.nextInt();
            }
            totalSum += getSignalStrength(wantedCycles, x, cycles);
            s.close();
        }
        System.out.println(totalSum);
        for (char[] chars : crt) {
            for (char aChar : chars) System.out.print(aChar);
            System.out.println();
        }
    }

}
