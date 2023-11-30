package day12;

import utils.InputReader;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solution {

    public static void main(String[] args) {
        BufferedReader br = InputReader.getBufferedReader("day12/input.txt");
        List<String> input = InputReader.getBufferedReaderLines(br);
        System.out.println(part1Solution(input));
        System.out.println(part2Solution(input));
    }

    private static List<Node> findAllNodesByHeight(Node[][] matrix, char... height) {
        List<Node> nodes = new ArrayList<>();
        for (Node[] nodes1 : matrix) {
            for (Node node : nodes1) {
                for (char c : height) if (node.getHeight() == c) nodes.add(node);
            }
        }
        return nodes;
    }

    private static List<Node> getNeighbours(Node[][] matrix, Node node) {
        List<Node> neighbours = new ArrayList<>();
        int row = node.getRow();
        int col = node.getCol();
        if (row > 0) neighbours.add(matrix[row - 1][col]);
        if (row < matrix.length - 1) neighbours.add(matrix[row + 1][col]);
        if (col > 0) neighbours.add(matrix[row][col - 1]);
        if (col < matrix[0].length - 1) neighbours.add(matrix[row][col + 1]);
        return neighbours;
    }

    private static void dijkstra(Node[][] matrix) {
        List<Node> unvisitedNodes = new ArrayList<>();
        for (Node[] nodes : matrix) Collections.addAll(unvisitedNodes, nodes);
        while (!unvisitedNodes.isEmpty()) {
            unvisitedNodes.sort(Comparator.comparing(Node::getDistanceFromStart));
            Node currentNode = unvisitedNodes.remove(0);
            List<Node> neighbors = getNeighbours(matrix, currentNode);
            for (Node neighbour : neighbors) {
                if (!unvisitedNodes.contains(neighbour) || neighbour.getHeight() > currentNode.getHeight() + 1)
                    continue;
                int distance = currentNode.getDistanceFromStart() + 1;
                if (distance < neighbour.getDistanceFromStart()) {
                    neighbour.setDistanceFromStart(distance);
                    neighbour.setParentNode(currentNode);
                }
            }
        }
    }

    private static Integer getPathSize(Node endNode) {
        Node currentNode = endNode;
        int pathSize = 0;
        while (currentNode.getParentNode() != null) {
            pathSize += 1;
            currentNode = currentNode.getParentNode();
        }
        return pathSize;
    }

    private static Node[][] getHeightMap(List<String> input) {
        Node[][] matrix = new Node[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i += 1) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j += 1) matrix[i][j] = new Node(line.charAt(j), Integer.MAX_VALUE, i, j);
        }
        return matrix;
    }

    private static Node getStartNode(Node[][] matrix) {
        return findAllNodesByHeight(matrix, 'S').get(0);
    }

    private static Node getEndNode(Node[][] matrix) {
        return findAllNodesByHeight(matrix, 'E').get(0);
    }

    private static Integer part1Solution(List<String> input) {
        Node[][] heightMap = getHeightMap(input);
        Node start = getStartNode(heightMap);
        Node end = getEndNode(heightMap);
        start.setDistanceFromStart(0);
        start.setHeight('a');
        end.setHeight('z');
        dijkstra(heightMap);
        return getPathSize(end);
    }

    private static Integer part2Solution(List<String> input) {
        Node[][] heightMap = getHeightMap(input);
        Node start = getStartNode(heightMap);
        Node end = getEndNode(heightMap);
        end.setHeight('z');
        List<Node> minElevationNodes = findAllNodesByHeight(heightMap, 'a');
        minElevationNodes.add(start);
        int minPathSize = Integer.MAX_VALUE;
        while (!minElevationNodes.isEmpty()) {
            Node minElevationNode = minElevationNodes.remove(0);
            minElevationNode.setDistanceFromStart(0);
            minElevationNode.setHeight('a');
            dijkstra(heightMap);
            int pathSize = getPathSize(end);
            minPathSize = Math.min(minPathSize, pathSize);
            for (Node[] nodes : heightMap) {
                for (Node node : nodes) {
                    node.setDistanceFromStart(Integer.MAX_VALUE);
                    node.setParentNode(null);
                }
            }
            end.setHeight('z');
        }
        return minPathSize;
    }

}
