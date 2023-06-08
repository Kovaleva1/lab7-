package commands;

import exceptions.NotUniqueIdException;
import exceptions.WrongCommandArgsException;
import exceptions.WrongModelsException;

import java.sql.SQLException;

public class Add extends ServerCommand {

    public Add() {
        super("add", "добавляет элемент в коллекцию", true, true);
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

            if (musicband == null || !musicband.validate()) {  //валидация моделек
                throw new WrongModelsException();
            }

            //добавляем в бд
            int id = MBDatabaseManager.addMusicBand(user, musicband);
            musicband.setId(id);
            musicband.setCreatorId(user.getId());


            //добавляем в коллекцию
            collectionManager.add(musicband);
        } catch (NotUniqueIdException | WrongCommandArgsException e) {
            console.write(e.toString());
        } catch (SQLException e) {
            console.write(e.toString());
            console.write("Добавить работника не получилось");
        }
    }
}