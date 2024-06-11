package lib;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import server.managers.CommandManager;
import server.commands.Command;
import java.io.Serializable;

public class ServerExchangeChannel {

    private InetSocketAddress target;
    private ServerSocketChannel channel;
    private Selector selector;
    private CommandManager commandManager;

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
                        handleRead(key);
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
        Message message = extractMessage(buffer);
        if (message != null)
            processMessage(message, clientChannel);
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
        ExitCode result = launchCommand(commandName, entity, clientChannel);
        //sendMessage(clientChannel, new Message(result.toString().getBytes()));
    }

    private ExitCode launchCommand(String userCommand, Serializable entity, SocketChannel clientChannel) {
        Command command = commandManager.getCommands().get(userCommand);
        switch (userCommand) {
            case "exit": {
                if (!commandManager.getCommands().get("exit").execute(userCommand, clientChannel)) return ExitCode.ERROR;
                else return ExitCode.EXIT;
            }
            default: {
                if (!command.execute(entity, clientChannel)) return ExitCode.ERROR;
            }
        }
        return ExitCode.OK;
    }

    public boolean sendMessage(SocketChannel clientChannel, Message message) {
        ByteBuffer buffer = ByteBuffer.wrap(SerializationUtils.serialize(message));
        try {
            while (buffer.hasRemaining()) {
                clientChannel.write(buffer);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
