package lib;

import java.io.*;
import java.nio.ByteBuffer;

public class Message implements Serializable {
    private String commandName;

    private Serializable entity;

    public Message(String commandName, Serializable entity) {
        this(commandName);
        this.entity = entity;
    }

    public Message(String commandName) {
        this.commandName = commandName;
    }

    public Message(Serializable entity) {
        this.entity = entity;
    }

    public Message(ByteBuffer buffer) {

        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);

        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Message message = null;
        try {
            message = (Message)in.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.entity = message.entity;
        this.commandName = message.commandName;

    }

    public Message(byte[] bytes) throws StreamCorruptedException, IOException {

        ObjectInputStream in = null;

        in = new ObjectInputStream(new ByteArrayInputStream(bytes));

        Message message = null;
        try {
            message = (Message)in.readObject();
        }
        catch (EOFException e) {
            message = null;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.entity = message.entity;
        this.commandName = message.commandName;

    }

    public Serializable getEntity() {
        return entity;
    }

    public String getCommandName() {
        return commandName;
    }
}