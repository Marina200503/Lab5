package client.commands;

import client.utility.Ask;
import lib.Console;
import lib.ExchangeChannel;
import lib.Message;
import lib.models.LabWork;

/**
 * обновляет значение элемента коллекции, id которого равен заданному
 */
public class UpdateId extends Command {
    private final Console console;
    private final ExchangeChannel exchangeChannel;

    public UpdateId(Console console, ExchangeChannel exchangeChannel) {
        super("update_id {element}", "обновить значение элемента коллекции, id которого равен заданному");
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
        long id = -1L;
        try {
            id = Integer.parseInt(arguments[1].trim());
        } catch (NumberFormatException e) {
            console.println("ID не распознан");
            return false;
        }

        console.println("* Создание новой лабораторной работы:");
        LabWork receivedLabWork = Ask.askLabWork(console);
        receivedLabWork.setId(id);

        exchangeChannel.sendMesssage(new Message("update_id", receivedLabWork));
        return true;
    }
}
