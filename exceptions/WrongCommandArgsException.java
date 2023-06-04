package exceptions;

public class WrongCommandArgsException extends Exception {
    @Override
    public String toString() {
        return "Неправильные аргументы команды!";
    }
}

