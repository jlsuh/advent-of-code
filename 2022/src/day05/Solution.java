package day05;

import utils.InputReader;

import java.io.BufferedReader;
import java.util.*;
import java.util.regex.Pattern;

public class Solution {

    public static void main(String[] args) {
        BufferedReader br = InputReader.getBufferedReader("day05/input.txt");
        List<String> lines = InputReader.getBufferedReaderLines(br);
        System.out.println(solutions(lines));
    }

    private static int getBlankIndex(List<String> lines) {
        int blankIndex = 0;
        while (!lines.get(blankIndex).equals("")) blankIndex += 1;
        return blankIndex;
    }

    private static List<List<String>> getProcessedInstructions(List<String> rawInstructions) {
        List<List<String>> processedInstructions = new ArrayList<>();
        for (String ln : rawInstructions) {
            List<String> instruction = Arrays.asList(ln.replaceAll("\\D", " ").trim().split("\\s+"));
            processedInstructions.add(instruction);
        }
        return processedInstructions;
    }

    private static List<Stack<String>> getStacks(List<String> stackNumbers, List<String> arrangements) {
        List<Stack<String>> stacks = new ArrayList<>();
        for (int i = 0; i < stackNumbers.size(); i++) stacks.add(new Stack<>());
        for (String arrangement : arrangements) {
            char[] row = arrangement.toCharArray();
            for (int j = 1; j < row.length; j += 4) if (row[j] != ' ') stacks.get(j / 4).push(String.valueOf(row[j]));
        }
        return stacks;
    }

    private static List<String> getStackNumbers(List<String> arrangements) {
        return Arrays.asList(Pattern.compile("\\s\\s\\s").matcher(arrangements.get(0).trim()).replaceAll(" ").split(" "));
    }

    private static String getPeakCrates(List<Stack<String>> stacks) {
        return stacks.stream().reduce("", (s, stack) -> s + stack.pop(), String::concat);
    }

    private static List<String> solutions(List<String> lines) {
        int blankIndex = getBlankIndex(lines);
        List<String> arrangements = lines.subList(0, blankIndex);
        Collections.reverse(arrangements);
        List<String> stackNumbers = getStackNumbers(arrangements);
        arrangements.remove(0);
        List<Stack<String>> stacks9000 = getStacks(stackNumbers, arrangements);
        List<Stack<String>> stacks9001 = getStacks(stackNumbers, arrangements);
        List<String> rawInstructions = lines.subList(blankIndex, lines.size());
        List<List<String>> processedInstructions = getProcessedInstructions(rawInstructions);
        processedInstructions.forEach(pi -> {
            int moves = Integer.parseInt(pi.get(0));
            int srcStack = Integer.parseInt(pi.get(1)) - 1;
            int destStack = Integer.parseInt(pi.get(2)) - 1;
            Stack<String> auxStack = new Stack<>();
            for (int i = 0; i < moves; i++) {
                stacks9000.get(destStack).push(stacks9000.get(srcStack).pop());
                auxStack.push(stacks9001.get(srcStack).pop());
            }
            while (!auxStack.isEmpty()) stacks9001.get(destStack).push(auxStack.pop());
        });
        return Arrays.asList(getPeakCrates(stacks9000), getPeakCrates(stacks9001));
    }

}
