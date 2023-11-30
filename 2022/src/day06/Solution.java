package day06;

import utils.InputReader;

import java.io.BufferedReader;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        BufferedReader br = InputReader.getBufferedReader("day06/input.txt");
        List<String> input = InputReader.getBufferedReaderLines(br);
        input.forEach(dataStream -> System.out.println(Arrays.asList(solution(dataStream, 4), solution(dataStream, 14))));
    }

    private static Boolean isStarterMarker(String str, int start, int end) {
        List<String> list = Arrays.asList(str.substring(start, end).split(""));
        Set<String> set = new HashSet<>(list);
        return list.size() == set.size();
    }

    private static Integer solution(String dataStream, int offset) {
        int pivot = 0;
        for (; ; ) {
            if (isStarterMarker(dataStream, pivot, pivot + offset)) return pivot + offset;
            else pivot += 1;
        }
    }

}
