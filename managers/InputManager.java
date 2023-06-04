package managers;

import commands.Command;
import consoles.Console;
import consoles.StandardConsole;
import exceptions.EndInputException;
import exceptions.EndInputWorkerException;
import exceptions.NoSuchCommandException;
import models.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

/**
 * Класс для работы с вводом
 */
public class InputManager {
    private Console console = new StandardConsole();
    private CollectionManager collectionManager;
    private User user;

    private static String stopMusicBand = "EXIT";  //слово, при котором обрывается ввод работника

    public InputManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    public InputManager(CollectionManager collectionManager, Console console) {
        this.collectionManager = collectionManager;
        this.console = console;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Console getConsole() {
        return console;
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    public Integer getInteger(String text, boolean positive) throws EndInputException, EndInputWorkerException {
        String tmp = ""; //временное хранение ввода
        Integer x;
        console.write(text);
        while (console.hasNext()) {
            tmp = console.getNextStr();
            if (tmp.equals(stopMusicBand)) {
                throw new EndInputWorkerException();
            }
            if (ValidateManager.isInteger(tmp)) {
                x = Integer.parseInt(tmp);
                if (x > 0 || (x <= 0 && !positive)) {
                    return x;
                }
            }
            console.write(text);
        }
        throw new EndInputException();
    }

    public Float getFloat(String text, boolean notNull, boolean positive) throws EndInputException, EndInputWorkerException {
        String tmp = ""; //временное хранение ввода
        Float x;
        console.write(text);
        while (console.hasNext()) {
            tmp = console.getNextStr();
            if (tmp.equals(stopMusicBand)) {
                throw new EndInputWorkerException();
            }
            if (tmp.equals("") && !notNull) {
                return null;
            }
            if (ValidateManager.isFloat(tmp)) {
                x = Float.parseFloat(tmp);
                if (x > 0 || (x <= 0 && !positive)) {
                    return x;
                }
            }
            console.write(text);
        }
        throw new EndInputException();
    }

    public MusicGenre getGenre() throws EndInputException, EndInputWorkerException {
        String text = "Введите жанр музыкальной группы или пустую строку для null. Варианты: ";
        for (MusicGenre el : MusicGenre.values()) {
            text += el + " ";
        }

        String tmp = ""; //временное хранение ввода
        console.write(text);
        while (console.hasNext()) {
            tmp = console.getNextStr();
            if (tmp.equals(stopMusicBand)) {
                throw new EndInputWorkerException();
            }
            if (tmp.equals("")) {
                return null;
            }
            try {
                return MusicGenre.valueOf(tmp);
            } catch (IllegalArgumentException e) {
                console.write(text);
            }
        }
        throw new EndInputException();
    }

    public LocalDate getDate(String text) throws EndInputException, EndInputWorkerException {
        String tmp = ""; //временное хранение ввода
        console.write(text);
        while (console.hasNext()) {
            tmp = console.getNextStr();
            if (tmp.equals(stopMusicBand)) {
                throw new EndInputWorkerException();
            }
            if (tmp.equals("")) {
                return null;
            }
            try {
                return LocalDate.parse(tmp);
            } catch (DateTimeParseException e) {
                console.write(text);
            }
        }
        throw new EndInputException();
    }

    public String getNotBlankString(String text, int minLength, boolean notNull) throws EndInputException, EndInputWorkerException {
        String tmp = ""; //временное хранение ввода
        console.write(text);
        while (console.hasNext()) {
            tmp = console.getNextStr();
            if (tmp.equals(stopMusicBand)) {
                throw new EndInputWorkerException();
            }
            if (!notNull && tmp.equals("")) {
                return null;
            }
            if (!tmp.isBlank() && tmp.length() >= minLength) {
                return tmp;
            }
            console.write(text);
        }
        throw new EndInputException();
    }


    public MusicBand getMusicBand() {
        try {
            String tmp = ""; //временное хранение ввода

            String name = getNotBlankString("Введите имя музыкальной группы:", 1, true);

            console.write("Введите координаты музыкальной (X, Y).");

            Integer x = getInteger("Введите X (целое число):", false);
            Integer y = getInteger("Введите Y (целое число):", false);

            Coordinates coordinates = new Coordinates(x, y);

            Integer sales = getInteger("Введите кол-во продаж музыкальной группы целое число или пустую строку для null:", false);
            MusicGenre genre = getGenre();
            Integer numberofPartisipants = getInteger("Введите номер выступления музыкальной группы целое число:", false);
            Integer singleCount = getInteger("Введите кол-во синглов музыкальной группы целое число:", false);


            Label label = new Label(sales);

            return new MusicBand(name, new Coordinates(Integer.valueOf(x), Integer.valueOf(y)), numberofPartisipants, singleCount,genre, label);
        } catch (EndInputException | EndInputWorkerException e) {
            return null;
        }
    }

    public void run() {
        Command command;
        CommandManager commandManager = new CommandManager(this, collectionManager);
        while (console.hasNext()) {
            String text = "Введите команду (help - чтобы узнать команды):";
            console.write(text);
            console.write("--------------------------");
            try {
                commandManager.executeCommand(console.getNextStr());
            } catch (NoSuchCommandException e) {
                console.write(e.toString());
            } catch (NoSuchElementException e) {
                console.write("");
            }
            console.write("--------------------------");
            console.write("");
        }
    }
}