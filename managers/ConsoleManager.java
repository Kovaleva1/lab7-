package managers;

import consoles.Console;

import java.util.Scanner;

public class ConsoleManager implements Console {
    private Scanner sc = new Scanner(System.in);


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