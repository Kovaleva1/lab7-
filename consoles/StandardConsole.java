package consoles;

import java.util.Scanner;

public class StandardConsole implements Console {
    private final Scanner sc = new Scanner(System.in);

    public boolean hasNext() {
        return true;
    }

    public String getNextStr() {
        return sc.nextLine().strip();
    }

    public void write(String text) {
        System.out.println(text);
    }
}