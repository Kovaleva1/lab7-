package consoles;

public class StringConsole implements Console {
    private String res = "";

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public String getNextStr() {
        return null;
    }

    @Override
    public void write(String text) {
        res += text + "\n";
    }

}
