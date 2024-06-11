package client;

import lib.Console;
import client.utility.Runner;
import lib.StandardConsole;
import lib.ExchangeChannel;
import client.managers.CommandManager;

import java.net.InetSocketAddress;

public class ClientMain {

    public static void main(String[] args) {

        InetSocketAddress target = new InetSocketAddress(1234);
        InetSocketAddress host = new InetSocketAddress(Integer.valueOf(System.getenv("CLIENT_PORT")));//add check
        ExchangeChannel channel = new ExchangeChannel(target, host);
        Console console = new StandardConsole();
        CommandManager commandManager = new CommandManager(console, channel);

        new Runner(console, commandManager).interactiveMode();

    }

}
