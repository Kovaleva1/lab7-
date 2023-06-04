package exceptions;

public class NoSuchCommandException extends Exception {
    @Override
    public String toString() {
        return "Несуществующая команда!";
    }
}