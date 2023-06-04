package commands;

import exceptions.WrongCommandArgsException;

import java.util.Arrays;
public class Help extends ServerCommand{
    private ServerCommand[] commands;

    public Help() {
        super("help", "выводит полную информацию о командах", false, false);
    }

    public void setCommands(ServerCommand[] commands) {
        this.commands = commands;
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
            Arrays.stream(commands).forEach(command -> console.write(command.toString()));
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}