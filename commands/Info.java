package commands;

import exceptions.WrongCommandArgsException;

public class Info extends ServerCommand {

    public Info() {
        super("info", "выводит информацию о коллекции", false, true);
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
            collectionManager.printInfo();
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}

