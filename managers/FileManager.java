package managers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class FileManager {
    public static String getTextFromFile(String fileName) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(fileName));
            StringBuilder s = new StringBuilder();
            int curB = inputStreamReader.read();
            while (curB != -1) {
                s.append((char) curB);
                curB = inputStreamReader.read();
            }
            inputStreamReader.close();
            return s.toString();
        } catch (IOException e) {
            return "ошибка ввода-вывода";
        }
    }
    public static void writeTextToFile(String fileName, String text) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileName));
            char[] chars = text.toCharArray();
            for (char c : chars) {
                outputStreamWriter.write(c);
            }
            outputStreamWriter.close();
        } catch (IOException e) {
            System.out.println("ошибка ввода-вывода");
        }
    }
}