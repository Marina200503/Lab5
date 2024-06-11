package server.managers;

import lib.ExchangeChannel;
import lib.ServerExchangeChannel;
import server.commands.*;
import lib.Console;

import java.util.LinkedHashMap;
import java.util.*;
/**
 * оперирует командами
 */
public class CommandManager {
    private final Map<String, Command> commands = new LinkedHashMap<>();

    public CommandManager(Console console, CollectionManager collectionManager, ServerExchangeChannel exchangeChannel) {
        register("help", new Help(console, this));
        register("info", new Info(console, collectionManager, exchangeChannel));
        register("max_by_id", new MaxById(console, collectionManager, exchangeChannel));
        register("show", new Show(console, collectionManager, exchangeChannel));
        register("add", new Add(console, collectionManager, exchangeChannel));
        register("update_id", new UpdateId(console, collectionManager, exchangeChannel));
        register("remove_by_id", new RemoveById(console, collectionManager, exchangeChannel));
        register("clear", new Clear(console, collectionManager, exchangeChannel));
        register("save", new Save(console, collectionManager, exchangeChannel));
        register("add_if_max", new AddIfIdMax(console, collectionManager, exchangeChannel));
        register("remove_greater", new RemoveGreater(console, collectionManager, exchangeChannel));
        register("print_field_descending_difficulty", new PrintFieldDescendingDifficulty(console, collectionManager, exchangeChannel));
        register("count_greater_than_difficulty", new CountGreaterThanDifficulty(console, collectionManager, exchangeChannel));
        register("head", new Head(console, collectionManager, exchangeChannel));
    }

    /**
     * @param commandName - название команды как будущий ключ
     * @param command     - экземпляр команды
     */
    public void register(String commandName, Command command) {
        commands.put(commandName, command);
    }

    /**
     * @return словарь команд
     */
    public Map<String, Command> getCommands() {
        return commands;
    }
}
