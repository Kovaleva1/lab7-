package exceptions;

public class UnavailableModelException extends Exception {
    @Override
    public String toString() {
        return "Эту  музыкальную группу создал другой пользователь!";
    }
}

