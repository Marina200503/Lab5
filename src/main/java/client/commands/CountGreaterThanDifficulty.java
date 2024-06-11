package client.commands;

import client.utility.Ask;
import lib.Console;
import lib.ExchangeChannel;
import lib.Message;
import lib.models.Difficulty;

/**
 * вывести количество элементов, значение поля difficulty которых больше заданного
 */
public class CountGreaterThanDifficulty extends Command {
    private final ExchangeChannel exchangeChannel;
    private final Console console;
    public CountGreaterThanDifficulty( Console console, ExchangeChannel exchangeChannel){
        super("count_greater_than_difficulty", "выводит количество элементов, значение поля difficulty которых больше заданного");
        this.exchangeChannel = exchangeChannel;
        this.console = console;
    }
    /**
     * выполняет команду
     *
     * @param args - аргументы команды
     * @return успешность выполнения команды
     */
    @Override
    public boolean execute(String[] args) {
        console.println("* Введите значение Difficulty для сравнения:");
        Difficulty userDifficulty = Ask.askDifficulty(console);// Считывание с консоли и проверка на валидность

        exchangeChannel.sendMesssage(new Message("count_greater_than_difficulty", userDifficulty));

        int k = (int)exchangeChannel.recieveMessage().getEntity();

        //int k = collectionManager.countGreaterThanDifficulty(userDifficulty);
        console.print("Количество элементов, значение поля difficulty которых больше заданного: " + k + "\n");
        return true;
    }
}
