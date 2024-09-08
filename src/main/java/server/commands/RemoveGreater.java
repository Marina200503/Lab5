package server.commands;
import lib.Message;
import lib.ServerExchangeChannel;
import lib.models.User;
import server.managers.CollectionManager;
import lib.models.LabWork;
import lib.Console;
import server.managers.SQLManager;

import java.io.Serializable;
import java.nio.channels.MembershipKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

/**
 * удаляет из коллекции все элементы, превышающие заданный
 */
public class RemoveGreater extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    private final ServerExchangeChannel exchangeChannel;

    public RemoveGreater(Console console, CollectionManager collectionManager, ServerExchangeChannel exchangeChannel){
        super("remove_greater {element}", "удалить из коллекции все элементы, превышающие заданный");
        this.console = console;
        this.collectionManager = collectionManager;
        this.exchangeChannel = exchangeChannel;
    }

    @Override
    public boolean execute(Serializable mlabWork, SocketChannel clientChannel, Message message) {
        User user = message.getUser();
        if (SQLManager.authenticateUser(user.getUserName(), user.getPassword()) != 0) {
            LabWork receivedLabWork = (LabWork) mlabWork;
            if (receivedLabWork != null && receivedLabWork.validate()) {
                int i = 0;
                ArrayList<LabWork> needRemove = new ArrayList<LabWork>();
                for (LabWork labWork : collectionManager.getCollection()) {
                    if (user.getUser_id() == (labWork).getUser_id() &&
                            labWork.getMinimalPoint() > receivedLabWork.getMinimalPoint()) {
                        needRemove.add(labWork);
                        i++;
                    }
                }
                for (LabWork labWork : needRemove)
                    collectionManager.getCollection().remove(labWork);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
