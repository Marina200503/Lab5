package server.commands;

import lib.Message;
import lib.ServerExchangeChannel;
import lib.models.LabWork;
import lib.models.User;
import server.managers.CollectionManager;

import lib.Console;
import server.managers.SQLManager;

import java.io.Serializable;
import java.nio.channels.SocketChannel;

/**
 * регистрирует пользователя
 */
public class Register extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final ServerExchangeChannel exchangeChannel;

    public Register(Console console, CollectionManager collectionManager, ServerExchangeChannel exchangeChannel) {
        super("register", "регестрация пользователя");
        this.console = console;
        this.collectionManager = collectionManager;
        this.exchangeChannel = exchangeChannel;
    }

    @Override
    public boolean execute(Serializable user, SocketChannel clientChannel, Message message) {
        SQLManager.registerUser(((User) user).getUserName(), ((User) user).getPassword());
        int id = SQLManager.authenticateUser(((User) user).getUserName(), ((User) user).getPassword());
        User newUser = new User(((User) user).getUserName(), ((User) user).getPassword(), id);
        return exchangeChannel.sendMessage(clientChannel, new Message("register", null, newUser));
    }
}
