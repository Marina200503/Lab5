package client.commands;

import client.utility.Ask;
import lib.models.User;
import lib.ExchangeChannel;
import lib.*;

import java.util.List;


public class Register extends Command{
    private Console console;
    private final ExchangeChannel exchangeChannel;
    public Register(Console console, ExchangeChannel exchangeChannel) {
        super("register", "регестрация пользователя");
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
            exchangeChannel.sendMesssage(new Message("register", user));
            Message message = exchangeChannel.recieveMessage();
            if (message == null)
                return false;

            Command.user =  message.getUser();
            return true;
    }
}
