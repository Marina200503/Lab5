package server.commands;

import lib.Console;
import server.managers.CommandManager;

import java.io.Serializable;
import java.nio.channels.SocketChannel;

/**
 * выводит справку по достпуным командам
 */
public class Help extends Command {
    private final Console console;
    private final CommandManager commandManager;

    public Help(Console console, CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(Serializable ent, SocketChannel clientChannel) {
        commandManager.getCommands().values().forEach(command -> {
            console.printTable(command.getName(), command.getDescription());
        });
        return true;
    }
}
