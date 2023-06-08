package managers;

import java.nio.file.Files;
import java.nio.file.Path;

public class ValidateManager {
    public static boolean isFloat(String value) {
        try {
            Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isFile(String value) {
        return Files.exists(Path.of(value));
    }
}