package day07;

import utils.InputReader;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

    private static final Integer FS_SIZE = 70000000;
    private static final Integer UPDATE_RUNNABLE_SIZE = 30000000;
    private static final Integer FIRST_PART_SIZE = 100000;

    public static void main(String[] args) {
        BufferedReader br = InputReader.getBufferedReader("day07/input.txt");
        List<String> lines = InputReader.getBufferedReaderLines(br);
        Directory root = new Directory(0, "/", null);
        List<DEntry> fsDirs = new ArrayList<>();
        fsDirs.add(root);
        System.out.println(part1Solution(lines, root, fsDirs));
        System.out.println(part2Solution(root, fsDirs));
    }

    private static Integer part1Solution(List<String> lines, Directory root, List<DEntry> fsDirs) {
        Directory cwd = root;
        for (String line : lines) {
            Scanner scanner = new Scanner(line);
            if (scanner.hasNext("\\$")) {
                scanner.next();
                String command = scanner.next();
                if (command.equals("cd")) {
                    String destDirName = scanner.next();
                    if (destDirName.equals("..")) {
                        cwd = cwd.getParent();
                    } else {
                        Directory destDir = cwd.findDirectoryEntryByName(destDirName);
                        if (destDir != null) {
                            cwd = destDir;
                        }
                    }
                }
            } else {
                DEntry newDirEntry = null;
                if (scanner.hasNextInt()) {
                    Integer size = scanner.nextInt();
                    String newFileName = scanner.next();
                    newDirEntry = new File(cwd, newFileName, size);
                } else if (scanner.hasNext()) {
                    scanner.next();
                    String newDirName = scanner.next();
                    newDirEntry = new Directory(cwd.getDepth() + 1, newDirName, cwd);
                    fsDirs.add(newDirEntry);
                }
                cwd.addEntry(newDirEntry);
            }
            scanner.close();
        }
        root.print(); // ¯\_( ツ )_/¯
        return fsDirs.stream()
                .filter(dEntry -> dEntry.getSize() <= FIRST_PART_SIZE)
                .toList().stream().map(DEntry::getSize)
                .reduce(0, Integer::sum);
    }

    private static Integer part2Solution(Directory root, List<DEntry> fsDirs) {
        final Integer totalSpaceUsed = root.getSize();
        List<DEntry> updateRunnableOnDelete =
                fsDirs.stream()
                        .filter(dEntry -> FS_SIZE - totalSpaceUsed + dEntry.getSize() >= UPDATE_RUNNABLE_SIZE)
                        .sorted((o1, o2) -> o2.getSize().compareTo(o1.getSize()))
                        .toList();
        return updateRunnableOnDelete.get(updateRunnableOnDelete.size() - 1).getSize();
    }

}
