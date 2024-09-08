package server.commands;

import lib.Message;
import lib.models.User;

import java.io.Serializable;
import java.nio.channels.SocketChannel;

public interface Executable {
    boolean execute(Serializable entity, SocketChannel clientChannel, Message message);
}