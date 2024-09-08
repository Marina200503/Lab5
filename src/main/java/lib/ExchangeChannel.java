package lib;

import lib.models.User;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//отправка и приём сообщений
public class ExchangeChannel {

    private InetSocketAddress target;
    private SocketChannel channel;
    private User user;

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
                    // Ожидание установления соединения
                }
            }
            while (buffer.hasRemaining()) {
                channel.write(buffer);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при отправке сообщения: " + e.getMessage());
        }


        return true;
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

    public User getUser() {
        return user;
    }
}
