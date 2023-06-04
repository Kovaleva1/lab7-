package exceptions;

public class NonUniqueLoginException extends WrongCommandArgsException {
    @Override
    public String toString() {
        return "Это имя пользователя занято!";
    }
}
