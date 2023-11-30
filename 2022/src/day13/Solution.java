package day13;

import utils.InputReader;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

    private static final Integer PAIR_STEP = 3;

    public static void main(String[] args) {
        BufferedReader br = InputReader.getBufferedReader("day13/input.txt");
        List<String> input = InputReader.getBufferedReaderLines(br);
        System.out.println(part1Solution(input));
        System.out.println(part2Solution(input));
    }

    private static String spacedBetween(String str) {
        return str.replaceAll("(?<=\\D)|(?=\\D)", "$0 ").trim();
    }

    private static List<Object> parsePacket(Scanner scanner) {
        scanner.next();
        List<Object> list = new ArrayList<>();
        while (scanner.hasNext()) {
            if (scanner.hasNext("\\[")) list.add(parsePacket(scanner));
            else if (scanner.hasNextInt()) list.add(scanner.nextInt());
            else if (scanner.hasNext("]")) {
                scanner.next();
                return list;
            } else scanner.next();
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    private static Boolean isInRightOrder(List<Object> left, List<Object> right) {
        List<Object> tempLeft = new ArrayList<>(left);
        List<Object> tempRight = new ArrayList<>(right);
        for (; ; ) {
            if (tempLeft.isEmpty() && tempRight.isEmpty()) return null;
            else if (tempLeft.isEmpty()) return true;
            else if (tempRight.isEmpty()) return false;
            Object leftValue = tempLeft.remove(0);
            Object rightValue = tempRight.remove(0);
            if (leftValue instanceof Integer && rightValue instanceof Integer) {
                if ((Integer) leftValue < (Integer) rightValue) return true;
                else if ((Integer) leftValue > (Integer) rightValue) return false;
            } else if (leftValue instanceof Integer && rightValue instanceof List<?>) {
                List<Object> newLeftSubList = new ArrayList<>();
                newLeftSubList.add(leftValue);
                tempLeft.add(0, newLeftSubList);
                tempRight.add(0, rightValue);
            } else if (leftValue instanceof List<?> && rightValue instanceof Integer) {
                List<Object> newRightSubList = new ArrayList<>();
                newRightSubList.add(rightValue);
                tempRight.add(0, newRightSubList);
                tempLeft.add(0, leftValue);
            } else if (leftValue instanceof List<?> && rightValue instanceof List<?>) {
                Boolean isInRightOrder = isInRightOrder((List<Object>) leftValue, (List<Object>) rightValue);
                if (isInRightOrder != null) return isInRightOrder;
            }
        }
    }

    private static List<Object> toPacket(List<String> input, Integer base) {
        String spacedInput = spacedBetween(input.get(base));
        Scanner scanner = new Scanner(spacedInput);
        List<Object> packet = parsePacket(scanner);
        scanner.close();
        return packet;
    }

    private static Integer part1Solution(List<String> input) {
        int sum = 0;
        for (int i = 0; i < input.size(); i += PAIR_STEP) {
            List<Object> leftPacket = toPacket(input, i);
            List<Object> rightPacket = toPacket(input, i + 1);
            if (Boolean.TRUE.equals(isInRightOrder(leftPacket, rightPacket))) sum += i / PAIR_STEP + 1;
        }
        return sum;
    }

    private static List<Object> extractPackets(List<String> input) {
        List<Object> packets = new ArrayList<>();
        for (int i = 0; i < input.size(); i += PAIR_STEP) {
            packets.add(toPacket(input, i));
            packets.add(toPacket(input, i + 1));
        }
        return packets;
    }

    @SuppressWarnings("unchecked")
    private static List<Object> sortedPackets(List<Object> packets, List<Object> firstDividerPacket, List<Object> secondDividerPacket) {
        List<Object> sorted = new ArrayList<>();
        sorted.add(firstDividerPacket);
        sorted.add(secondDividerPacket);
        for (int i = 0; i < packets.size(); i += 1) {
            List<Object> toInsert = (List<Object>) packets.get(i);
            for (int j = 0; j < sorted.size(); j += 1) {
                List<Object> toCompare = (List<Object>) sorted.get(j);
                if (j == sorted.size() - 1) {
                    if (Boolean.TRUE.equals(isInRightOrder(toCompare, toInsert))) {
                        sorted.add(packets.remove(i--));
                        break;
                    }
                } else {
                    List<Object> toCompareNext = (List<Object>) sorted.get(j + 1);
                    if (Boolean.TRUE.equals(isInRightOrder(toCompare, toInsert)) && Boolean.TRUE.equals(isInRightOrder(toInsert, toCompareNext))) {
                        sorted.add(j + 1, packets.remove(i--));
                        break;
                    } else if (Boolean.TRUE.equals(isInRightOrder(toInsert, toCompare))) {
                        sorted.add(j, packets.remove(i--));
                        break;
                    }
                }
            }
        }
        return sorted;
    }

    private static Integer packetDividerIndex(List<Object> packets, List<Object> packetDivider) {
        return packets.indexOf(packetDivider) + 1;
    }

    private static Integer part2Solution(List<String> input) {
        List<Object> firstSubDividerPacket = new ArrayList<>();
        List<Object> secondSubDividerPacket = new ArrayList<>();
        firstSubDividerPacket.add(2);
        secondSubDividerPacket.add(6);
        List<Object> firstDividerPacket = new ArrayList<>();
        List<Object> secondDividerPacket = new ArrayList<>();
        firstDividerPacket.add(firstSubDividerPacket);
        secondDividerPacket.add(secondSubDividerPacket);
        List<Object> packets = extractPackets(input);
        List<Object> sorted = sortedPackets(packets, firstDividerPacket, secondDividerPacket);
        Integer firstDividerPacketIndex = packetDividerIndex(sorted, firstDividerPacket);
        Integer secondDividerPacketIndex = packetDividerIndex(sorted, secondDividerPacket);
        return firstDividerPacketIndex * secondDividerPacketIndex;
    }

}
