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
 * авторизует пользователя
 */
public class Login extends Command  {
    private final Console console;
    private final CollectionManager collectionManager;
    private final ServerExchangeChannel exchangeChannel;

    public Login(Console console, CollectionManager collectionManager, ServerExchangeChannel exchangeChannel) {
        super("login", "Авторизоваться");
        this.console = console;
        this.collectionManager = collectionManager;
        this.exchangeChannel = exchangeChannel;
    }

    @Override
    public boolean execute(Serializable user, SocketChannel clientChannel, Message message) {
        if (SQLManager.authenticateUser(((User) user).getUserName(), ((User) user).getPassword())!=0){
            int id = SQLManager.authenticateUser(((User) user).getUserName(), ((User) user).getPassword());
            User user1 = new User(((User) user).getUserName(), ((User) user).getPassword(), id);
            return exchangeChannel.sendMessage(clientChannel, new Message("Login", null, user1));}
        else{
            return false;
        }
    }
}
