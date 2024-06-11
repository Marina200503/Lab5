package server.commands;

import java.io.Serializable;
import java.nio.channels.SocketChannel;

public interface Executable {
    boolean execute(Serializable entity, SocketChannel clientChannel);
}