package exceptions;

public class EndInputException extends Exception {
    @Override
    public String toString() {
        return "Неполная информация!";
    }
}
