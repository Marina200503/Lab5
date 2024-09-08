package client.commands;

import lib.Console;
import lib.ExchangeChannel;
import lib.Message;


/**
 * очищает коллекцию
 */
public class Clear extends Command {
    private final Console console;
    private final ExchangeChannel exchangeChannel;

    public Clear(Console console, ExchangeChannel exchangeChannel) {
        super("clear", "очистить коллекцию");
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

        exchangeChannel.sendMesssage(new Message("clear", null, Command.user));
        console.println("Коллекция очищена!");
        return true;
        }

    }
