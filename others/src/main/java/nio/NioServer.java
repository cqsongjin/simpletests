package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public void start() throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        Selector selector = Selector.open();
        server.configureBlocking(false);
        server.bind(new InetSocketAddress("127.0.0.1", 8080));
        server.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            int rdChannels = selector.select(100);
            if (rdChannels == 0) continue;
            Set<SelectionKey> selectKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    System.out.println("accept");
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socket = serverSocketChannel.accept();
                    socket.configureBlocking(false);
                    socket.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    System.out.println("read");
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    SocketChannel channel1 = (SocketChannel) key.channel();
                    int readBytes = 0;
                    try {
                        readBytes = channel1.read(buffer);
                    }catch (IOException e) {
                        e.printStackTrace();
                        channel1.close();
                        iterator.remove();
                        break;
                    }
                    if (readBytes > 0) {
                        buffer.flip();
                        byte[] bytes = new byte[buffer.remaining()];
                        buffer.get(bytes);
                        String message = new String(bytes, "utf-8");
                        System.out.println("this message is : " + message);
                        buffer.clear();
                        buffer.put((message + ",too!").getBytes());
                        buffer.flip();
                        channel1.write(buffer);
                    }

                } else if (key.isWritable()) {
                    System.out.println("write");
                }
                iterator.remove();
            }
        }

    }

    public static void main(String[] args) {
        try {
            new NioServer().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
