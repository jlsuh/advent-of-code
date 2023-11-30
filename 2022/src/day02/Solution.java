package day02;

import utils.InputReader;

import java.io.BufferedReader;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        BufferedReader br = InputReader.getBufferedReader("day02/input.txt");
        Scanner s = new Scanner(br);
        int totalScore = 0;
        int totalDecryptedScore = 0;
        while (s.hasNextLine()) {
            String line = s.nextLine();
            char right = line.charAt(2);
            char left = line.charAt(0);
            // Part 1
            Option playerOption = right == 'X' ? Option.ROCK : right == 'Y' ? Option.PAPER : Option.SCISSORS;
            totalScore += playerOption.getOutcome(left);
            // Part 2
            Decryptor oppDecryptor = left == 'A' ? Decryptor.A : left == 'B' ? Decryptor.B : Decryptor.C;
            Option decryptedOption = oppDecryptor.getOutcome(right);
            totalDecryptedScore += decryptedOption.getOutcome(left);
        }
        s.close();
        // Part 1
        System.out.println("Total score: " + totalScore);
        // Part 2
        System.out.println("Total decrypted score: " + totalDecryptedScore);
    }

}
