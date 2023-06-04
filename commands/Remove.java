package commands;

import exceptions.NonExistentId;
import exceptions.UnavailableModelException;
import exceptions.WrongCommandArgsException;
import managers.ValidateManager;
import models.MusicBand;

import java.sql.SQLException;

/**
 * Команда remove_by_id id.
 * Удаляет работника по id из коллекции.
 */
public class Remove extends ServerCommand {

    public Remove() {
        super("remove_by_id", "удаляет музыкальную группу по id из коллекции",
                false, true);
    }

    @Override
    public void validateArgs(String[] args) throws NonExistentId, WrongCommandArgsException, UnavailableModelException {
        if (args.length != 1 || !ValidateManager.isInteger(args[0])) {
            throw new WrongCommandArgsException();
        }
        if (!collectionManager.existsId(Integer.parseInt(args[0]))) {
            throw new NonExistentId();
        }
        MusicBand musicBand = collectionManager.getMusicBandById(Integer.parseInt(args[0]));
        if (musicBand.getCreatorId() != user.getId()) {
            throw new UnavailableModelException();
        }
    }

    @Override
    public void execute(String[] args) {
        try {
            validateArgs(args);
            //удаляем в бд
            int count = MBDatabaseManager.removeMusicBand(user, collectionManager.getMusicBandById(Integer.parseInt(args[0])));
            if (count == 0) {
                throw new UnavailableModelException();
            }
            //удаляем в коллекции
            collectionManager.remove(Integer.parseInt(args[0]));

        } catch (WrongCommandArgsException | NonExistentId | UnavailableModelException e) {
            console.write(e.toString());
        } catch (SQLException e) {
            console.write("Удалить музыкальную группу не получилось");
        }
    }
}
