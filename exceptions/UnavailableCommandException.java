package exceptions;

public class UnavailableCommandException  extends Exception {
    @Override
    public String toString() {
        return "Эта команда только для зарегистрированных пользователей!";
    }
}