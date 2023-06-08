package commands;

import exceptions.WrongCommandArgsException;
import managers.AuthManager;

public class Logout extends ServerCommand {
    private AuthManager authManager = new AuthManager();

    public Logout() {
        super("logout", "выходит из аккаунта",
                false, true);
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException {
        if (args.length != 0) {
            throw new WrongCommandArgsException();
        }
    }

    @Override
    public void execute(String[] args) {
        try {
            validateArgs(args);
            this.user = null;
            console.write("Вы вышли из аккаунта");
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}
