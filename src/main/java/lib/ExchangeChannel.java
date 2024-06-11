package lib;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ExchangeChannel {

    private InetSocketAddress target;
    private SocketChannel channel;

    public ExchangeChannel(InetSocketAddress target, InetSocketAddress host) {
        this(host);
        this.target = target;
    }

    public ExchangeChannel(InetSocketAddress host) {
        try {
            channel = SocketChannel.open();
            channel.socket().setSoTimeout(200000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean sendMesssage(Message message) {
        if (target == null) return false;

        ByteBuffer buffer = ByteBuffer.wrap(SerializationUtils.serialize(message));
        try {
            if (!channel.isConnected()) {
                channel.connect(target);
                while (!channel.finishConnect()) {
                    // Wait for connection to be established
                }
            }
            while (buffer.hasRemaining()) {
                channel.write(buffer);
            }
            return true;
        } catch (IOException e) {
            return false;
        }

    }

    public Message recieveMessage() {
        ByteBuffer buffer = ByteBuffer.allocate(4000);
        try {
            channel.read(buffer);
            buffer.flip();
        } catch (IOException e) {
            System.out.println("Адрес недоступен!");
            //throw new RuntimeException(e);
        }

        Message message = extractMessage(buffer);
        return message;
    }

    public Message recieveMessageWithTimeOut() {
        ByteBuffer buffer = ByteBuffer.allocate(4000);
        try {
            channel.socket().setSoTimeout(200000);
            channel.read(buffer);
            buffer.flip();
        } catch (SocketTimeoutException ex) {
            return null;
        } catch (IOException e) {
            System.out.println("Адрес недоступен!");
            throw new RuntimeException(e);
        }

        Message message = extractMessage(buffer);
        return message;
    }

    private Message extractMessage(ByteBuffer buffer) {
        Message msg = null;
        try {
            msg = new Message(buffer.array());
        } catch (StreamCorruptedException e) {
            //throw new RuntimeException(e);
        } catch (IOException e) {
            //throw new RuntimeException(e);
        }
        return msg;
    }
}
