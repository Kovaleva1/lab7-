package commands;

import consoles.Console;
import exceptions.WrongCommandArgsException;
import managers.*;
import consoles.FileConsole;

public class ExecuteScript extends ServerCommand {
    private static int depth = 0, maxDepth = 5;
    private CollectionManager collectionManager;

    public ExecuteScript(CollectionManager collectionManager) {
        super("execute_script", "выполняет команды из файла", false, true);
        this.collectionManager = collectionManager;
    }

    public static void setMaxDepth(int maxDepth) {
        ExecuteScript.maxDepth = maxDepth;
    }

    public void validateArgs(String[] args) throws WrongCommandArgsException {
        if (args.length != 1 || !ValidateManager.isFile(args[0])) {
            throw new WrongCommandArgsException();
        }
    }

    public void execute(String[] args) {
        try {
            validateArgs(args);
            String fileName = args[0];
            if (depth >= maxDepth) {
                console.write("Превышена глубина рекурсии!");
                return;
            }
            console.write("Начинаем обработку файла с командами");
            depth++;
            Console fileConsole = new FileConsole(fileName);
            InputManager newInputManager = new InputManager(collectionManager, fileConsole);
            newInputManager.setUser(user);
            newInputManager.run();
            depth--;
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}