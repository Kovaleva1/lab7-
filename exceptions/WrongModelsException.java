package exceptions;

public class WrongModelsException extends WrongCommandArgsException {
    @Override
    public String toString() {
        return "Некорректная модель!";
    }
}
