package managers;

import commands.*;
import consoles.Console;
import consoles.StandardConsole;
import exceptions.*;
import models.MusicBand;
import models.User;

import java.util.LinkedList;
import java.util.TreeMap;

public class CommandManager {
    private final ServerCommand[] serverCommands;
    private final InputManager inputManager;
    private final Console console = new StandardConsole();

    private final TreeMap<String, ServerCommand> strToCommands = new TreeMap<>();
    //название команды, объект класса этой команды

    private final CollectionManager collectionManager;
    private final LinkedList<ServerCommand> history = new LinkedList<>();

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
                new Auth(), new Register(), new Logout(), new UserInfo(),
                help, new Exit(), new ExecuteScript(collectionManager)
        };
        help.setCommands(serverCommands);
        strToCommands.clear();
        for (ServerCommand command : serverCommands) {
            strToCommands.put(command.getName(), command);
        }
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
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
        res.setHistory(history);

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
        } else if (res instanceof ExecuteScript) {
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
            } catch (EndInputException | EndInputMBException e) {
                return;
            }
        }

        res.execute(args);
        inputManager.setUser(res.getUser());
        history.add(res);
    }
}

