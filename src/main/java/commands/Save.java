package commands;

import commands.Command;
import managers.CollectionManager;
import managers.JsonManager;
import utility.Console;
/**
 * сохраняет коллекцию в файл
 */
public class Save extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

   public Save(Console console, CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.console = console;
        this.collectionManager = collectionManager;
   }
    /**
     * выполняет команду
     *
     * @param arguments - аргументы команды
     * @return успешность выполнения команды
     */
    @Override
    public boolean execute(String[] arguments) {
        new JsonManager().save(collectionManager.getCollection());
        console.println("МЫ СОХРАНЕНЫ!!!");
        return true;
    }
}
