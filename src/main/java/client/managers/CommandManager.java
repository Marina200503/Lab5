package client.managers;

import lib.Console;
import lib.ExchangeChannel;
import client.commands.*;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * оперирует командами
 */
public class CommandManager {
    private final Map<String, Command> commands = new LinkedHashMap<>();

    private final Map<String, Command> unauthorizedCommands = new LinkedHashMap<>();

    public CommandManager(Console console, ExchangeChannel exchangeChannel) {
        register("help", new Help(console, this));
        register("info", new Info(console, exchangeChannel));
        register("max_by_id", new MaxById(console, exchangeChannel));
        register("show", new Show(console, exchangeChannel));
        register("add", new Add(console, exchangeChannel));
        register("update_id", new UpdateId(console, exchangeChannel));
        register("remove_by_id", new RemoveById(console, exchangeChannel));
        register("clear", new Clear(console, exchangeChannel));
        register("save", new Save(console, exchangeChannel));
        register("execute_script", new ExecuteScriptFileName(console));
        register("exit", new Exit(console));
        register("add_if_max", new AddIfIdMax(console, exchangeChannel));
        register("remove_greater", new RemoveGreater(console, exchangeChannel));
        register("print_field_descending_difficulty", new PrintFieldDescendingDifficulty(console, exchangeChannel));
        register("count_greater_than_difficulty", new CountGreaterThanDifficulty(console, exchangeChannel));
        register("head", new Head(console, exchangeChannel));

        unauthorizedCommands.put("help", new Help(console, this));
        unauthorizedCommands.put("login", new Login(console, exchangeChannel));
        unauthorizedCommands.put("register", new Register(console, exchangeChannel));
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
        if (Command.user == null || Command.user.getUser_id() == 0)
            return unauthorizedCommands;
        return commands;
    }
}
