package managers;
import commands.*;
import utility.Console;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.*;
public class CommandManager {
    private final Map<String, Command> commands = new LinkedHashMap<>();
    private final List<String> commandHistory = new ArrayList<>();
    public CommandManager(Console console, CollectionManager collectionManager) {
            register("help", new Help(console, this));
            register("info", new Info(console, collectionManager));
            register("max_by_id", new MaxById(console, collectionManager));
            register("show", new Show(console, collectionManager));
            register("add", new Add(console, collectionManager));
            register("update", new UpdateId(console, collectionManager));
            register("remove_by_id", new RemoveById(console, collectionManager));
            register("clear", new Clear(console, collectionManager));
            register("save", new Save(console, collectionManager));
            register("execute_script", new ExecuteScriptFileName(console));
            register("exit", new Exit(console));
            register("add_if_max", new AddIfIdMax(console, collectionManager));
            register("remove_greater", new RemoveGreater(console, collectionManager));
            register("print_field_descending_difficulty", new PrintFieldDescendingDifficulty(console, collectionManager));
            register("count_greater_than_difficulty", new CountGreaterThanDifficulty(console, collectionManager));
            register("head", new Head(console, collectionManager));
    }

    /**
     * @param commandName - название команды как будущий ключ
     * @param command - экземпляр команды
     */
    public void register (String commandName, Command command) {
        commands.put(commandName, command);
    }

    /**
     * @return словарь команд
     */
    public Map<String, Command> getCommands() {
        return commands;
    }

    /**
     * добавляет команду в историю
     * @param commandName - название команды
     */
    public void addToHistory(String commandName){
        commandHistory.add(commandName);
    }
}