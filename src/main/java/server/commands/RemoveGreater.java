package server.commands;
import lib.ServerExchangeChannel;
import server.managers.CollectionManager;
import lib.models.LabWork;
import lib.Console;

import java.io.Serializable;
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
    public boolean execute(Serializable mlabWork, SocketChannel clientChannel) {
        LabWork receivedLabWork = (LabWork)mlabWork;
        if (receivedLabWork != null && receivedLabWork.validate()) {
            int i = 0;
            ArrayList<LabWork> needRemove = new ArrayList<LabWork>();
            for (LabWork labWork : collectionManager.getCollection()) {
                if (labWork.getMinimalPoint() > receivedLabWork.getMinimalPoint()){
                    needRemove.add(labWork);
                    i++;
                }
            }
            for (LabWork labWork:needRemove)
                collectionManager.getCollection().remove(labWork);
            return true;
        } else {
            return false;
        }
    }
}
