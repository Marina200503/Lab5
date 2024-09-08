package client.commands;

import client.utility.Ask;
import lib.Console;
import lib.ExchangeChannel;
import lib.Message;
import lib.models.LabWork;


//добавление элемента

/**
 * добавляет новый элемент в коллекцию
 */
public class Add extends Command {
    private final Console console;
    private final ExchangeChannel exchangeChannel;
    public Add(Console console, ExchangeChannel exchangeChannel) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.exchangeChannel = exchangeChannel;
    }

    /**
     * Выполняет команду
     * @param arguments - аргументы команды
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {
        console.println("* Создание новой лабораторной работы:");
        LabWork d = Ask.askLabWork(console);
        if (d != null && d.validate()) {
            exchangeChannel.sendMesssage(new Message("add", d, Command.user));
            console.println("Лабораторная работа успешно добавлена!");
            return true;
        }
        else{
            console.printError("Поля лабораторной работы не валидны! Лабораторная работа не создана!");
            return false;
        }}


    }
