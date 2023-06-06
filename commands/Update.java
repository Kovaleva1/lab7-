package commands;

import exceptions.NonExistentId;
import exceptions.UnavailableModelException;
import exceptions.WrongCommandArgsException;
import exceptions.WrongModelsException;
import managers.ValidateManager;
import models.MusicBand;

import java.sql.SQLException;

public class Update extends ServerCommand {

    public Update() {
        super("update", "обновляет музыкальную группу по id на основе заданной музыкальной группу",
                true, true);
    }

    @Override
    public void validateArgs(String[] args) throws NonExistentId, WrongCommandArgsException, UnavailableModelException {
        if (args.length != 1 || !ValidateManager.isInteger(args[0])) {
            throw new WrongCommandArgsException();
        }
        if (!collectionManager.existsId(Integer.parseInt(args[0]))) {
            throw new NonExistentId();
        }
        MusicBand oldmusicband = collectionManager.getMusicBandById(Integer.parseInt(args[0]));
        if (oldmusicband.getCreatorId() != user.getId()) { //если у модельки другой создатель
            throw new UnavailableModelException();
        }
    }

    @Override
    public void execute(String[] args) {
        try {
            validateArgs(args);
            //валидация моделек
            if (musicband == null || !musicband.validate()) {
                throw new WrongModelsException();
            }
            //обновляем в бд
            int count = MBDatabaseManager.updateMusicBand(user, Integer.parseInt(args[0]), musicband);
            if (count == 0) {
                throw new UnavailableModelException();
            }
            //обновляем в коллекции
            collectionManager.update(Integer.parseInt(args[0]), musicband);
        } catch (WrongCommandArgsException | NonExistentId | UnavailableModelException e) {
            console.write(e.toString());
        } catch (SQLException e) {
            console.write(e.toString());
            console.write("Обновить работника не получилось");
        }
    }
}