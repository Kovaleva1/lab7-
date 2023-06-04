package commands;

import exceptions.WrongCommandArgsException;

public class Exit extends ServerCommand {
    public Exit() {
        super("exit", "завершает программу", false, false);
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
            System.exit(0);
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}