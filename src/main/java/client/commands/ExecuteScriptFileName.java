package client.commands;
import lib.Console;


/**
 * выполняет скрипт из файла
 */
public class ExecuteScriptFileName extends Command {
    private final Console console;
    public ExecuteScriptFileName(Console console){
        super("execute_script <file_name>", "исполнить скрипт из указанного файла");
        this.console = console;
    }

    /**
     * выполняет команду
     * @param arguments - аргументы команды
     * @return успешность выполнения команды
     */
    @Override
    public boolean execute(String[] arguments) {

            if (arguments[1].isEmpty()) {
                console.println("Неправильное количество аргументов!");
                console.println("Использование: '" + getName() + "'");
                return false;
            }
            console.println("Выполнение скрипта '" + arguments[1] + "'...");
            return true;

        }

}