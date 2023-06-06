package commands;

import exceptions.NonUniqueLoginException;
import exceptions.WrongCommandArgsException;
import managers.AuthManager;
import models.User;

public class Register extends ServerCommand {
    private AuthManager authManager = new AuthManager();

    public Register() {
        super("register", "производит регистрацию пользователя",
                false, false);
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException {
        if (args.length != 2) {
            throw new WrongCommandArgsException();
        }
        if (authManager.checkUserName(args[0])) {//если такой логин существует
            throw new NonUniqueLoginException();
        }
    }

    @Override
    public void execute(String[] args) {
        try {
            validateArgs(args);
            User user = new User(args[0], args[1]);
            boolean success = authManager.register(user);
            if (success) {
                this.user = user;
                console.write("Регистрация прошла успешно");
            } else {
                console.write("Не получилось зарегистрироваться");
            }
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}