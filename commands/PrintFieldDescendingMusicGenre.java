package commands;

import exceptions.WrongCommandArgsException;

public class PrintFieldDescendingMusicGenre extends ServerCommand {

    public PrintFieldDescendingMusicGenre() {
        super("print_field_descending_genre",
                "выводит значения поля genre всех элементов в порядке убывания", false, true);
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
            collectionManager.printFieldDescendingMusicGenre();
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}