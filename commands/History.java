package commands;

import exceptions.WrongCommandArgsException;

public class History extends ServerCommand {

    public History() {
        super("history", "выводит последние 11 команд", false, true);
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
            history.stream().skip(Math.max(0, history.size() - 11)).forEach(command -> console.write(command.getName()));
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}