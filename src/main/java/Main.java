import commands.*;
import managers.*;
import utility.*;
import utility.Runner;
public class Main {
    public static void main(String[] args) {
        CommandManager commandManager = new CommandManager();
        CollectionManager collectionManager = new CollectionManager();
        Console console = new StandardConsole();

        String jsonPath = System.getenv("JSON_PATH");
//        filePath = "C:\\Users\\New\\Desktop\\TestJson.json";

        if (jsonPath  == null) {
            console.println("Введите имя загружаемого файла в переменную среды 'lab'");
            System.exit(1);
        }

        collectionManager.loadCollection();
        new Runner(console, commandManager).interactiveMode();
    }
}
