package server.commands;

import lib.Console;
import lib.Message;
import lib.ServerExchangeChannel;
import server.managers.CollectionManager;

import java.io.Serializable;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * вывести информацию о коллекции
 */
public class Info extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    private final ServerExchangeChannel exchangeChannel;
    public Info(Console console, CollectionManager collectionManager, ServerExchangeChannel exchangeChannel) {
        super("info", "вывести информацию о коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
        this.exchangeChannel = exchangeChannel;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(Serializable ent, SocketChannel clientChannel) {
        LocalDateTime lastInitTime = collectionManager.getLastInitTime();
        String lastInitTimeString = (lastInitTime == null) ? "в данной сессии инициализации еще не происходило" : //условие ? значение_если_истина : значение_если_ложь.
                lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();

        LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
        String lastSaveTimeString = (lastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();


        List list = List.of("Сведения о коллекции:",
        " Тип: " + collectionManager.getCollection().getClass().toString(),
        " Количество элементов: " + collectionManager.getCollection().size(),
        " Дата последнего сохранения: " + lastSaveTimeString,
        " Дата последней инициализации: " + lastInitTimeString);
        return exchangeChannel.sendMessage(clientChannel, new Message("info", (Serializable) list));
}
}