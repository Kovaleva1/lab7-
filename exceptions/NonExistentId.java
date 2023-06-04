package exceptions;

public class NonExistentId extends Exception {
    @Override
    public String toString() {
        return "Такой id не существует";
    }
}
