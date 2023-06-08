package commands;

import exceptions.WrongCommandArgsException;

public class UserInfo extends ServerCommand {

    public UserInfo() {
        super("user_info", "выводит информацию о текущем пользователе",
                false, false);
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
            if (user == null) {
                console.write("Вы не вошли в аккаунт");
            } else {
                console.write(user.toString());
            }
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}
