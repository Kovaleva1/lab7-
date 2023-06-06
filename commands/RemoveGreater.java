package commands;

import exceptions.WrongCommandArgsException;
import exceptions.WrongModelsException;
import models.MusicBand;

import java.sql.SQLException;
import java.util.LinkedList;

public class RemoveGreater extends ServerCommand {

    public RemoveGreater() {
        super("remove_greater", "удаляет музыкальную группу, которые больше заданного",
                true, true);
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException {
        if (args.length != 0) {
            throw new WrongCommandArgsException();
        }
    }

    public void execute(String[] args) {
        try {
            validateArgs(args);
            //валидация моделек
            if (musicband == null || !musicband.validate()) {
                throw new WrongModelsException();
            }
            //удаляем в бд
            for (MusicBand other : new LinkedList<>(collectionManager.getLinkedList())) {
                if (other.getCreatorId() == user.getId() && other.compareTo(musicband) > 0) {
                    MBDatabaseManager.removeMusicBand(user, musicband);
                }
            }
            collectionManager.removeGreater(musicband, user.getId());
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        } catch (SQLException e) {
            console.write("Удалить музыкальную группу не получилось");
        }
    }
}