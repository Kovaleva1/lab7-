package exceptions;

public class WrongCredentialsException extends WrongCommandArgsException {
    @Override
    public String toString() {
        return "Ошибка в логине или пароле!";
    }
}
