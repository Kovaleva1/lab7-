package commands;

import exceptions.WrongCommandArgsException;

public class Show extends ServerCommand {

    public Show() {
        super("show", "выводит элементы коллекции", false, true);
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
            collectionManager.printElements();
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}

