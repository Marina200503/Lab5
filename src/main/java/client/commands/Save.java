package client.commands;

import lib.Console;
import lib.ExchangeChannel;
import lib.Message;

/**
 * сохраняет коллекцию в файл
 */
public class Save extends Command {
    private final Console console;
    private final ExchangeChannel exchangeChannel;

   public Save(Console console, ExchangeChannel exchangeChannel) {
        super("save", "сохранить коллекцию в файл");
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
        exchangeChannel.sendMesssage(new Message("save"));
        //new JsonManager().save(collectionManager.getCollection());
        console.println("МЫ СОХРАНЕНЫ!!!");
        return true;
    }
}
