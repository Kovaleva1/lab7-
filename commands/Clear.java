package commands;

import exceptions.WrongCommandArgsException;

import java.sql.SQLException;

public class Clear extends ServerCommand {
    public Clear() {
        super("clear", "очищает коллекцию", false, true);
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
            int count = MBDatabaseManager.clearMusicBand(user);
            console.write("Удалено музыкальных групп: " + count);
            collectionManager.clear(user.getId());
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        } catch (SQLException e) {
            console.write("Очистить коллекцию не получилось");
        }
    }

}
