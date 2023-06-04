package commands;

import exceptions.WrongCommandArgsException;
import managers.ValidateManager;

public class FilterByNumberofParticipants extends ServerCommand {

    public FilterByNumberofParticipants() {
        super("filter_by_number_of_participants", "выводит музыкальную группу с заданным номером выступления", false, true);
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException {
        if (args.length != 1 || !(args[0].equals("null") || ValidateManager.isFloat(args[0]))) {
            throw new WrongCommandArgsException();
        }
    }

    @Override
    public void execute(String[] args) {
        try {
            validateArgs(args);
            Integer numberofParticipants = null;
            if (ValidateManager.isInteger(args[0])) {
                numberofParticipants = Integer.valueOf(args[0]);
            }
            collectionManager.getFilterByNumberofParticipants(numberofParticipants).forEach(musicBand -> console.write(musicBand.toString()));
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}
