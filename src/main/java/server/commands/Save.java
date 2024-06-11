package server.commands;

import lib.ServerExchangeChannel;
import server.managers.CollectionManager;
import server.managers.JsonManager;
import lib.Console;

import java.io.Serializable;
import java.nio.channels.SocketChannel;

/**
 * сохраняет коллекцию в файл
 */
public class Save extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final ServerExchangeChannel exchangeChannel;

   public Save(Console console, CollectionManager collectionManager, ServerExchangeChannel exchangeChannel) {
        super("save", "сохранить коллекцию в файл");
        this.console = console;
        this.collectionManager = collectionManager;
        this.exchangeChannel = exchangeChannel;
   }

    @Override
    public boolean execute(Serializable ent, SocketChannel clientChannel) {
        new JsonManager().save(collectionManager.getCollection());
        console.println("МЫ СОХРАНЕНЫ!!!");
        return true;
    }
}
