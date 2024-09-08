package client.commands;

import client.utility.Ask;
import lib.models.User;
import lib.ExchangeChannel;
import lib.*;

import java.util.List;


public class Login extends Command{
    private Console console;
    private final ExchangeChannel exchangeChannel;
    public Login(Console console, ExchangeChannel exchangeChannel) {
        super("login", "Авторизация пользователя");
        this.console = console;
        this.exchangeChannel = exchangeChannel;
    }
    @Override
    public boolean execute(String[] arguments) {
        // Ввод имени пользователя и пароля
        console.println("Введите имя пользователя и пароль: ");
        // Создаем объект User с введенными данными
        User user = Ask.askUser(console);
        //прием айдишника
        exchangeChannel.sendMesssage(new Message("login", user));
        Message message = exchangeChannel.recieveMessage();
        if (message == null)
            return false;

        Command.user = message.getUser();
        return true;
    }
}
