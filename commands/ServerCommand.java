package commands;

import consoles.Console;
import database.ConnectionManager;
import database.MBDatabaseManager;
import managers.CollectionManager;
import models.User;
import server.Configuration;

import java.util.LinkedList;

public abstract class ServerCommand extends AbstractCommand {
    protected Console console;

    protected CollectionManager collectionManager;
    protected LinkedList<ServerCommand> history;

    protected ConnectionManager connectionManager = new ConnectionManager(Configuration.getDbUrl(),
            Configuration.getDbLogin(),
            Configuration.getDbPass()
    );
    protected MBDatabaseManager MBDatabaseManager = new MBDatabaseManager(connectionManager);
    protected User user;

    public ServerCommand(String name, String description, boolean withMusicband, boolean onlyUsers) {
        super(name, description, withMusicband, onlyUsers);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void setHistory(LinkedList<ServerCommand> history) {
        this.history = history;
    }

    public abstract void execute(String[] args);
}