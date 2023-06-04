package commands;

import exceptions.NonExistentId;
import exceptions.WrongCommandArgsException;
import consoles.Console;
import models.MusicBand;

public abstract class CommandWithMB extends Command {
    protected CommandWithMB(String name, String description, Console console) {
        super(name, description, console);
    }

    public abstract void setMusicBand(MusicBand musicBand);

    public abstract void validateArgs(String[] args) throws WrongCommandArgsException, NonExistentId;
}

