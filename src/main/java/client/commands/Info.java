package client.commands;

import lib.Console;
import lib.ExchangeChannel;
import lib.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * вывести информацию о коллекции
 */
public class Info extends Command {
    private final Console console;
    private final ExchangeChannel exchangeChannel;

    public Info(Console console, ExchangeChannel exchangeChannel) {
        super("info", "вывести информацию о коллекции");
        this.console = console;
        this.exchangeChannel = exchangeChannel;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {

        exchangeChannel.sendMesssage(new Message("info"));
        Message message = exchangeChannel.recieveMessage();
        if (message == null)
            return false;

        List<String> list = (List<String>)message.getEntity();
        System.out.println(list);
        return true;
    }
}