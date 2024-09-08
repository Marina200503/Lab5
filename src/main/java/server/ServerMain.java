package server;

import lib.Console;
import lib.Message;
import lib.ServerExchangeChannel;
import lib.StandardConsole;
import server.managers.CollectionManager;
import server.managers.CommandManager;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * Main класс
 */
public class ServerMain {
    /**
     * метод запуска команды
     */

    static CollectionManager collectionManager;
    static ServerExchangeChannel exchangeChannel;
    static CommandManager commandManager;
//запуск серверной части приложения
    public static void main(String[] args) {
        collectionManager = new CollectionManager();
        collectionManager.loadCollection();
        exchangeChannel = new ServerExchangeChannel(new InetSocketAddress(1234));
        Console console = new StandardConsole();
        commandManager = new CommandManager(console, collectionManager, exchangeChannel);

        new Thread(() -> {
            Scanner in = new Scanner(System.in);
            while (true) {
                System.out.print("$ ");
                var t = in.nextLine();
                if (t.equals("save"))
                    commandManager.getCommands().get("save").execute(new String[]{"save", ""}, null, null);
                if (t.equals("exit"))
                    System.exit(0);
            }
        }).start();

        exchangeChannel.setCommandManager(commandManager); // Передача CommandManager в ExchangeChannel
        exchangeChannel.start();  // Запуск обработки клиентов
    }
}
