package client.commands;

import lib.Console;
import lib.ExchangeChannel;
import lib.Message;

/**
 * вывести в стандартный поток вывода все элементы коллекции в строковом представлении
 */
public class Show extends Command {
    private final Console console;
    private final ExchangeChannel exchangeChannel;

    public Show(Console console, ExchangeChannel exchangeChannel) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.console = console;
        this.exchangeChannel = exchangeChannel;

    }

    /**
     * Выполняет команду
     *
     * @param arguments - аргументы команды
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {

        exchangeChannel.sendMesssage(new Message("show", null, Command.user));
        System.out.println(exchangeChannel.recieveMessage().getEntity());
        return true;

    }
}
