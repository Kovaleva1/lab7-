package managers;

import commands.*;
import consoles.Console;
import consoles.StandardConsole;
import database.CommandDatabaseManager;
import exceptions.*;
import loggers.Logger;
import loggers.StandardLogger;
import models.MusicBand;
import models.User;
import models.UserRole;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeMap;

public class CommandManager {
    private final ServerCommand[] serverCommands;
    private final InputManager inputManager;
    private String dataFileName;
    private final Console console = new StandardConsole();

    private final TreeMap<String, ServerCommand> strToCommands = new TreeMap<>();
    //название команды, объект класса этой команды

    private final CollectionManager collectionManager;
    private final LinkedList<ServerCommand> history = new LinkedList<>();

    private CommandDatabaseManager commandDatabaseManager;

    Logger logger = new StandardLogger();

    public CommandManager(InputManager inputManager, CollectionManager collectionManager) {
        this.inputManager = inputManager;
        this.collectionManager = collectionManager;

        Help help = new Help();
        serverCommands = new ServerCommand[]{new Info(),
                new Show(), new Add(),
                new Update(), new Remove(), new Clear(),
                new Head(), new RemoveGreater(),
                new History(), new FilterByNumberofParticipants(),
                new PrintDescending(),
                new PrintFieldDescendingMusicGenre(),
                new Auth(), new Register(), new Logout(),
                 help, new Exit(), new ExecuteScript(collectionManager)
        };
        help.setCommands(serverCommands);

        for (ServerCommand command : serverCommands) {
            strToCommands.put(command.getName(), command);
        }
    }


    public void setMinUserRoles() {
        for (ServerCommand command : serverCommands) {
            try {
                String minUserRole = commandDatabaseManager.getMinUserRole(command.getName());
                command.setMinUserRole(UserRole.valueOf(minUserRole));
            } catch (SQLException e) {
                logger.write("Нет информация о минимальной роли для команды " + command.getName());
            }
        }
    }

    public CommandDescription[] getCommandDescriptions() {
        return Arrays.stream(serverCommands)
                .map(ServerCommandDescription::new)
                .toArray(CommandDescription[]::new);
    }

    public CommandDescription[] getCommandDescriptions(UserRole userRole) {
        return Arrays.stream(serverCommands).filter(command -> command.getMinUserRole().ordinal() <= userRole.ordinal())
                .map(ServerCommandDescription::new)
                .toArray(CommandDescription[]::new);
    }

    public CommandDescription[] getCommandDescriptionsForUnauthorizedUser() {
        return Arrays.stream(serverCommands).filter(command -> !command.isOnlyUsers())
                .map(ServerCommandDescription::new)
                .toArray(CommandDescription[]::new);
    }

    public ServerCommand getServerCommandFromCommandDescription(CommandDescription command) {
        ServerCommand serverCommand;
        //каждый раз создаём новый экземпляр
        try {
            serverCommand = strToCommands.get(command.getName()).getClass().getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        serverCommand.setArgs(command.getArgs());
        serverCommand.setMusicBand(command.getMusicBand());

        return serverCommand;
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    public LinkedList<ServerCommand> getHistory() {
        return history;
    }

    public void serverValidateCommand(ServerCommand command) throws NonExistentId, WrongCommandArgsException,
            UnavailableCommandException, UnavailableModelException {
        //если команда только для зарегистрированных пользователей, а текущий пользователь не вошёл в аккаунт,
        //то не даём ему провести валидацию и выполнить команду
        if (command.isOnlyUsers() && command.getUser() == null) {
            throw new UnavailableCommandException();
        }
        command.setCollectionManager(collectionManager);
        command.validateArgs(command.getArgs());
    }

    public void executeCommand(String strCommand) throws NoSuchCommandException {
        String[] subsCommand = strCommand.split("\\s+");
        strCommand = subsCommand[0];
        String[] args = new String[subsCommand.length - 1];
        for (int i = 1; i < subsCommand.length; i++) {
            args[i - 1] = subsCommand[i];
        }

        if (!strToCommands.containsKey(strCommand)) { //если нет такой команды
            throw new NoSuchCommandException();
        }

        ServerCommand res = strToCommands.get(strCommand);

        User user = inputManager.getUser();

        if (user == null && res.isOnlyUsers()) {
            console.write("Надо зарегистрироваться, чтоб выполнить эту команду");
            return;
        }

        res.setCollectionManager(collectionManager);
        res.setConsole(console);
        res.setUser(user);

        if (res.isWithMusicband()) {
            try {
                res.validateArgs(args);
            } catch (WrongCommandArgsException | NonExistentId | UnavailableModelException e) {
                console.write(e.toString());
                return;
            }
            MusicBand musicBand = inputManager.getMusicBand();
            if (musicBand == null) return;
            res.setMusicBand(musicBand);
        }
        else if (res instanceof ExecuteScript) {
            try {
                ((ExecuteScript) res).validateArgs(args);
                //макс глубина рекурсии спрашивается только тогда, когда мы работаем со стандартным вводом
                if (console instanceof ConsoleManager) {
                    int maxDepth = inputManager.getInteger("Введите максимальную глубину рекурсии: ", true);
                    ExecuteScript.setMaxDepth(maxDepth);
                }
            } catch (WrongCommandArgsException e) {
                console.write(e.toString());
                return;
            } catch (EndInputException | EndInputWorkerException e) {
                return;
            }
        }
        res.execute(args);
        inputManager.setUser(res.getUser());
        history.add(res);
    }
}

