package task12_2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import task11.Deck;

public class BlackjackServer {
    private static final int BUST = 21;
    //private final List<Player> players;
    //private final Dealer dealer;
    private Stack<Person> queue;
    private List<Person> standing;
    private Deck deck;
    private static List<SocketChannel> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress("localhost", 3000);
        serverChannel.bind(address);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT); 

        long startTime = System.currentTimeMillis();
        //System.currentTimeMillis() - startTime < 15000
        while (true) {
            for (SelectionKey key : selector.selectedKeys()) {
                ServerSocketChannel server = (ServerSocketChannel) key.channel();
                SocketChannel client = server.accept();
                client.configureBlocking(false);
                client.register(selector, SelectionKey.OP_WRITE);
                clients.add(client);
                System.out.println(client.getRemoteAddress() + "connected and added to list");
            }
        }
        //System.out.println("15s over");
    }
}
