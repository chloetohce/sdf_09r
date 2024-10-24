package task12_2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class TestClient {
    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress("localhost", 3000);
        SocketChannel cliet = SocketChannel.open(address);
        System.out.println("Client started");
        
    }
}
