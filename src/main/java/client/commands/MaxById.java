package client.commands;

import lib.Console;
import lib.ExchangeChannel;
import lib.Message;
import lib.models.LabWork;

/**
 * выводит любой объект из коллекции, значение поля id которого является максимальным
 */
public class MaxById extends Command {
    private final Console console;
    private final ExchangeChannel exchangeChannel;

    public MaxById(Console console, ExchangeChannel exchangeChannel) {
        super("max_by_id", "вывести любой объект из коллекции, значение поля Id которого является максимальным");
        this.console = console;
        this.exchangeChannel = exchangeChannel;
    }

    /**
     * выполняет команду
     *
     * @param arguments - аргументы команды
     * @return успешность выполнения команды
     */
    @Override
    public boolean execute(String[] arguments) {

        exchangeChannel.sendMesssage(new Message("max_by_id", null, Command.user));
        console.println((LabWork) exchangeChannel.recieveMessage().getEntity());
        return true;

    }
}