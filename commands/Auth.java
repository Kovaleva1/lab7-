package commands;

import exceptions.WrongCommandArgsException;
import exceptions.WrongCredentialsException;
import managers.AuthManager;
import models.User;

public class Auth extends ServerCommand {
    private AuthManager authManager = new AuthManager();

    public Auth() {
        super("auth", "производит вход пользователя", false, false);
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException {
        if (args.length != 2) {
            throw new WrongCommandArgsException();
        }
        if (!authManager.checkUserPass(args[0], args[1])) {//если такого логина и пароля не существует
            throw new WrongCredentialsException();
        }
    }

    @Override
    public void execute(String[] args) {
        try {
            validateArgs(args);
            User user = new User(args[0], args[1]);
            boolean success = authManager.auth(user);
            ;
            if (success) {
                this.user = user;
                console.write("Вы вошли в свой аккаунт");
            } else {
                console.write("Не получилось войти");
            }

        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}
