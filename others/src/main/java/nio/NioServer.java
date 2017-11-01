package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
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
                SelectionKey key1 = iterator.next();
                if (key1.isAcceptable()) {
                    System.out.println("accept");
                } else if (key1.isConnectable()) {
                    System.out.println("connection");
                } else if (key1.isReadable()) {
                    System.out.println("read");
                } else if (key1.isWritable()) {
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
