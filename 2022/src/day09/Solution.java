package day09;

import utils.InputReader;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        BufferedReader br = InputReader.getBufferedReader("day09/input.txt");
        List<String> lines = InputReader.getBufferedReaderLines(br);
        System.out.println(part1Solution(lines));
        System.out.println(part2Solution(lines));
    }

    private static Boolean isVisitedNode(List<Vector2> nodes, Vector2 node) {
        return nodes.stream().anyMatch(n -> n.isOverlapping(node));
    }

    private static void transformPart1(List<Vector2> visited, List<Vector2> knots, Integer moveOnX, Integer moveOnY, Integer times) {
        for (int i = 0; i < times; i += 1) {
            for (int j = 1; j < knots.size(); j += 1) {
                Vector2 head = knots.get(j - 1);
                Vector2 tail = knots.get(j);
                Vector2 originalHeadPos = new Vector2(head.getX(), head.getY());
                head.setX(head.getX() + moveOnX);
                head.setY(head.getY() + moveOnY);
                if (!tail.isWithinRange(head, Math.sqrt(2))) {
                    tail.setX(originalHeadPos.getX());
                    tail.setY(originalHeadPos.getY());
                }
                if (!isVisitedNode(visited, tail)) {
                    visited.add(new Vector2(tail.getX(), tail.getY()));
                }
            }
        }
    }

    private static Integer part1Solution(List<String> lines) {
        List<Vector2> visited = new ArrayList<>();
        visited.add(new Vector2(0, 0));
        List<Vector2> knots = Arrays.asList(new Vector2(0, 0), new Vector2(0, 0));
        for (String move : lines) {
            Scanner s = new Scanner(move);
            char dir = s.next().charAt(0);
            int times = s.nextInt();
            switch (dir) {
                case 'U' -> transformPart1(visited, knots, 0, 1, times);
                case 'D' -> transformPart1(visited, knots, 0, -1, times);
                case 'L' -> transformPart1(visited, knots, -1, 0, times);
                case 'R' -> transformPart1(visited, knots, 1, 0, times);
            }
            s.close();
        }
        return visited.size();
    }

    private static Integer[] getDiagonalMove(Vector2 head, Vector2 tail) {
        Integer[] moves = new Integer[2];
        Integer headX = head.getX(), headY = head.getY();
        Integer tailX = tail.getX(), tailY = tail.getY();
        moves[0] = headX.compareTo(tailX);
        moves[1] = headY.compareTo(tailY);
        return moves;
    }

    private static void transformPart2(List<Vector2> knots, Vector2 headerOriginalPos, List<Vector2> visited) {
        boolean prevDiagonallyMoved = false;
        Vector2 tailOriginalPos;
        for (int i = 1; i < knots.size(); i += 1) {
            Vector2 head = knots.get(i - 1);
            Vector2 tail = knots.get(i);
            if (!tail.isWithinRange(head, Math.sqrt(2))) {
                tailOriginalPos = new Vector2(tail.getX(), tail.getY());
                if (!prevDiagonallyMoved) {
                    if (tail.differentialVectorNorm2(headerOriginalPos) == Math.sqrt(2)) {
                        prevDiagonallyMoved = true;
                        Integer[] diagonalMove = getDiagonalMove(head, tail);
                        tail.setX(tail.getX() + diagonalMove[0]);
                        tail.setY(tail.getY() + diagonalMove[1]);
                    } else {
                        tail.setX(headerOriginalPos.getX());
                        tail.setY(headerOriginalPos.getY());
                    }
                } else {
                    Integer[] diagonalMove = getDiagonalMove(head, tail);
                    tail.setX(tail.getX() + diagonalMove[0]);
                    tail.setY(tail.getY() + diagonalMove[1]);
                }
                headerOriginalPos = tailOriginalPos;
            } else {
                break;
            }
            Vector2 lastKnot = knots.get(knots.size() - 1);
            if (!isVisitedNode(visited, lastKnot)) {
                visited.add(new Vector2(lastKnot.getX(), lastKnot.getY()));
            }
        }
    }

    private static Integer part2Solution(List<String> lines) {
        List<Vector2> visited = new ArrayList<>();
        visited.add(new Vector2(0, 0));
        List<Vector2> knots = new ArrayList<>();
        for (int i = 0; i < 10; i += 1) knots.add(new Vector2(0, 0));
        for (String move : lines) {
            Scanner s = new Scanner(move);
            char dir = s.next().charAt(0);
            int times = s.nextInt();
            for (int i = 0; i < times; i += 1) {
                Vector2 head = knots.get(0);
                Vector2 headOriginalPos = new Vector2(head.getX(), head.getY());
                switch (dir) {
                    case 'U' -> {
                        head.setX(head.getX());
                        head.setY(head.getY() + 1);
                    }
                    case 'D' -> {
                        head.setX(head.getX());
                        head.setY(head.getY() - 1);
                    }
                    case 'L' -> {
                        head.setX(head.getX() - 1);
                        head.setY(head.getY());
                    }
                    case 'R' -> {
                        head.setX(head.getX() + 1);
                        head.setY(head.getY());
                    }
                }
                transformPart2(knots, headOriginalPos, visited);
            }
            s.close();
        }
        return visited.size();
    }

}
