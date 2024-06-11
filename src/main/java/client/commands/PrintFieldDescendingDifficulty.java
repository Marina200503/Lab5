package client.commands;

import lib.Console;
import lib.ExchangeChannel;
import lib.Message;

/**
 * вывести значение поля Difficulty всех элементов в порядке убывания
 */
public class PrintFieldDescendingDifficulty extends Command {
    private Console console;
    private ExchangeChannel exchangeChannel;
    public PrintFieldDescendingDifficulty(Console console, ExchangeChannel exchangeChannel){
        super("print_field_descending_difficulty", "вывести значения поля difficulty всех элементов в порядке убывания");
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
        exchangeChannel.sendMesssage(new Message("print_field_descending_difficulty"));
        System.out.println(exchangeChannel.recieveMessage().getEntity());
        return true;
    }
}