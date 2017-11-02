package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
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
        channel.register(selector, SelectionKey.OP_CONNECT);
        channel.connect(new InetSocketAddress("127.0.0.1", 8080));
        while (true) {
            int rdChannels = selector.select(100);
            if (rdChannels == 0) continue;
            Set<SelectionKey> selectKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isValid()) {
                    SocketChannel channel1 = (SocketChannel) key.channel();
                    if (key.isConnectable()) {
                        if (channel1.finishConnect()) {
                            System.out.println("connection");
                            String message = "hello word!";
                            ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
//                            ByteBuffer buffer = ByteBuffer.allocate(1024);
//                            buffer.put(message.getBytes("utf-8"));
                            channel1.write(buffer);
                            key.channel().register(selector, SelectionKey.OP_READ);
                        }

                    } else if (key.isReadable()) {
                        System.out.println("read");
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int readBytes = channel1.read(buffer);
                        if (readBytes > 0) {
                            buffer.flip();
                            byte[] bytes = new byte[buffer.remaining()];
                            buffer.get(bytes);
                            String message = new String(bytes, "utf-8");
                            System.out.println("this message is : " + message);
                        }
                    } else if (key.isWritable()) {
                        System.out.println("write");
                    }
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
