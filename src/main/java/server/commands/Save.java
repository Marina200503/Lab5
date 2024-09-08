package server.commands;

import lib.Message;
import lib.ServerExchangeChannel;
import lib.models.User;
import server.managers.CollectionManager;

import lib.Console;
import server.managers.SQLManager;

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
    public boolean execute(Serializable ent, SocketChannel clientChannel, Message message) {
        User user = message.getUser();
        if (SQLManager.authenticateUser(user.getUserName(), user.getPassword()) != 0) {
            SQLManager.createLabWork(collectionManager.getCollection());
            console.println("МЫ СОХРАНЕНЫ!!!");
            return true;
        }
        return false;
    }
}
