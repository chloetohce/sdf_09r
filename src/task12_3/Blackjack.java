package task12_3;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class Blackjack {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("localhost", 3000);
        String id = System.console().readLine("Enter id: ");
        OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
        PrintWriter bw = new PrintWriter(osw, true);
        InputStreamReader isr = new InputStreamReader(socket.getInputStream());
        BufferedReader br = new BufferedReader(isr);
        bw.write(id);
        bw.flush();

        // try {
            while (!socket.isClosed()) {
                char[] buff = new char[2048];
                br.read(buff);
                Thread.sleep(100);
                String msg = new String(buff);
    
                int pos = msg.indexOf(":") == -1 ? 0 : msg.indexOf(":") + 1;
                System.out.println(msg.substring(pos));
                if (msg.startsWith("QUERY")) {
                    String choice = System.console().readLine("> ").trim();
                    bw.println(choice);
                }
            }
        // } catch (SocketException e) {
        //     System.out.println("Game ended. Exiting program.");;
        // }
    }
}
