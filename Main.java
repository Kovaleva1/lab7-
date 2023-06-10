import consoles.FileConsole;
import database.ConnectionManager;
import database.MBDatabaseManager;
import loggers.Logger;
import loggers.StandardLogger;
import managers.CollectionManager;
import managers.InputManager;
import models.MusicBand;
import server.Configuration;

import java.sql.SQLException;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        final Logger logger = new StandardLogger();
        String[] strings = new FileConsole("credentials.txt").getLines();
        if (strings.length != 3) {
            logger.write("Некорректный файл credentials.txt");
            return;
        }

        Configuration.setDbUrl(strings[0].trim());
        Configuration.setDbLogin(strings[1].trim());
        Configuration.setDbPass(strings[2].trim());

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.write("Не найден драйвер!");
            return;
        }

        ConnectionManager connectionManager = new ConnectionManager(Configuration.getDbUrl(),
                Configuration.getDbLogin(),
                Configuration.getDbPass()  //pgpass
        );

        MBDatabaseManager databaseManager = new MBDatabaseManager(connectionManager);

        LinkedList<MusicBand> startMusicBands = null;
        try {
            startMusicBands = (LinkedList<MusicBand>) databaseManager.loadMusicBands();
        } catch (SQLException e) {
            logger.write("Загрузить коллекцию из базы данных не получилось");
            logger.writeError(e.toString());
            System.exit(1);
        }
        CollectionManager collectionManager = new CollectionManager();
        collectionManager.setMusicBands(startMusicBands);

        InputManager inputManager = new InputManager(collectionManager);

        inputManager.run();
    }
}