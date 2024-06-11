package client.commands;

import lib.Console;
import lib.ExchangeChannel;
import lib.Message;

/**
 * удалить элемент из коллекции по его id
 */
public class RemoveById extends Command {
    private Console console;
    private ExchangeChannel exchangeChannel;
    public RemoveById(Console console, ExchangeChannel exchangeChannel) {
        super("remove_by_id id", "удалить элемент из коллекции по его id");
        this.console = console;
        this.exchangeChannel = exchangeChannel;
    }
    /**
     * выполняет команду
     * @param arguments - аргументы команды
     * @return успешность выполнения команды
     */
    @Override
    public boolean execute(String[] arguments) {
        if (arguments[1].isEmpty()) {
            console.println("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
            return false;
        }
        int id = -1;
        try { id = Integer.parseInt(arguments[1].trim()); } catch (NumberFormatException e) { console.println("ID не распознан"); return false; }

        exchangeChannel.sendMesssage(new Message("remove_by_id", id));

        return true;
    }
}
