package lib;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

import lib.models.User;
import server.commands.Executable;
import server.managers.CommandManager;
import server.commands.Command;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

//основной механизм обработки ввода/вывода для сервера.
// Он отвечает за установление соединений с клиентами, чтение данных из сокетов, извлечение сообщений,
// обработку команд и отправку ответов клиентам.
public class ServerExchangeChannel {

    private InetSocketAddress target;
    private ServerSocketChannel channel;
    private Selector selector;
    private CommandManager commandManager;
    // Пул потоков для многопоточного чтения запросов
    private final ExecutorService readThreadPool = Executors.newCachedThreadPool();

    // Пул потоков для многопоточной обработки запросов
    private final ForkJoinPool processPool = new ForkJoinPool();
    private final ExecutorService sendThreadPool = Executors.newFixedThreadPool(10);

    public ServerExchangeChannel(InetSocketAddress target, InetSocketAddress host) {
        this(host);
        this.target = target;
    }

    public ServerExchangeChannel(InetSocketAddress host) {
        try {
            selector = Selector.open();
            channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            channel.bind(host);
            channel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public void start() {
        try {
            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();

                    if (key.isAcceptable()) {
                        handleAccept(key);
                    } else if (key.isReadable()) {
                        // Запуск handleRead в новом потоке
                        new Thread(() -> {
                            try {
                                handleRead(key);
                            } catch (IOException e) {
                                //throw new RuntimeException(e);
                            }
                        }).start();
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);
    }

    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(4000);
        int bytesRead = clientChannel.read(buffer);

        if (bytesRead == -1) {
            clientChannel.close();
            return;
        }

        buffer.flip();

        // Запускаем задачу чтения в новом потоке из CachedThreadPool
        readThreadPool.execute(() -> {
            Message message = extractMessage(buffer);
            if (message != null) {
                // Запускаем задачу обработки сообщения в ForkJoinPool
                processPool.execute(() -> processMessage(message, clientChannel));
            }
        });
    }

    private Message extractMessage(ByteBuffer buffer) {
        try {
            return new Message(buffer.array());
        } catch (StreamCorruptedException e) {
            //throw new RuntimeException(e);
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processMessage(Message message, SocketChannel clientChannel) {
        String commandName = message.getCommandName();
        Serializable entity = message.getEntity();
        User user = message.getUser();
        ExitCode result = launchCommand(commandName, entity, clientChannel, message);
        //sendMessage(clientChannel, new Message(result.toString().getBytes()));
    }

    private ExitCode launchCommand(String userCommand, Serializable entity, SocketChannel clientChannel, Message message) {
        Executable command = commandManager.getCommands().get(userCommand);
        switch (userCommand) {
            case "exit": {
                if (!commandManager.getCommands().get("exit").execute(userCommand, clientChannel, message)) return ExitCode.ERROR;
                else return ExitCode.EXIT;
            }
            default: {
                if (!command.execute(entity, clientChannel, message)) return ExitCode.ERROR;
            }
        }
        return ExitCode.OK;
    }

    // Метод для отправки сообщений с использованием Fixed Thread Pool
    public boolean sendMessage(SocketChannel clientChannel, Message message) {
        sendThreadPool.execute(() -> {
            ByteBuffer buffer = ByteBuffer.wrap(SerializationUtils.serialize(message));
            try {
                while (buffer.hasRemaining()) {
                    clientChannel.write(buffer);
                }
            } catch (IOException e) {
                System.out.println("Ошибка при отправке сообщения: " + e.getMessage());
            }
        });
        return true; // Возвращаем true, чтобы указать, что задача отправки добавлена в пул потоков
    }
}
