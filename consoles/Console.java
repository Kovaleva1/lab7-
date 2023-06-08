package consoles;

public interface Console {

    boolean hasNext();

    String getNextStr();

    void write(String text);
}