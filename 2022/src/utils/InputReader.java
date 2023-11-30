package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InputReader {

    public static BufferedReader getBufferedReader(String pathSuffix) {
        String path = new File("src/".concat(pathSuffix)).getAbsolutePath();
        File file = new File(path);
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
        InputStreamReader isr = new InputStreamReader(fis);
        return new BufferedReader(isr);
    }

    public static List<String> getBufferedReaderLines(BufferedReader br) {
        List<String> lines = new ArrayList<>();
        try {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return lines;
    }

}
