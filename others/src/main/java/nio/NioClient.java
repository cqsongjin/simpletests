package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioClient {
    public void start() throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        Selector selector = Selector.open();
        SelectionKey key = channel.register(selector, SelectionKey.OP_CONNECT);
        channel.connect(new InetSocketAddress("127.0.0.1", 8080));
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
                    key.channel().register(selector, SelectionKey.OP_READ);
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
            new NioClient().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
