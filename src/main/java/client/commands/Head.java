package client.commands;

import lib.Console;
import lib.ExchangeChannel;
import lib.Message;
import lib.models.User;
import lib.models.LabWork;
import server.managers.SQLManager;

/**
 * выводит первый элемент коллекции
 */
public class Head extends Command {
    private final Console console;
    private final ExchangeChannel exchangeChannel;
    private LabWork head;

    public Head(Console console, ExchangeChannel exchangeChannel) {
        super("head", "вывести первый элемент коллекции");
        this.console = console;
        this.exchangeChannel = exchangeChannel;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {

        exchangeChannel.sendMesssage(new Message("head", null, Command.user));
        Message mess = exchangeChannel.recieveMessage();
        head = (LabWork) mess.getEntity();
        //head = collectionManager.getCollection().getFirst();
        console.println("Первый элемент коллекции: " + head);
        return true;

        }
    }
