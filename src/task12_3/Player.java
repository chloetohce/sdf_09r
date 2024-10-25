package task12_3;

import java.io.*;
import java.net.*;
import task11.card.*;

public class Player implements Person {
    private Hand hand;
    private final String id;
    private final Socket conn;
    private final BufferedReader br;
    private final PrintWriter bw;

    public Player(Socket conn) throws IOException {
        this.hand = new Hand();
        this.conn = conn;
        this.br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        this.bw = new PrintWriter(new OutputStreamWriter(conn.getOutputStream()), true);
        char[] buff = new char[1024];
        br.read(buff);
        this.id = new String(buff).trim();
        System.out.println("Created player " + id);
    }

    @Override
    public void hit(Card c) {
        hand.add(c);
    }

    public void printMenu() {
        bw.println("What would you like to do?");
        bw.println("1. Display current hand.");
        bw.println("2. Get another card.");
        bw.println("3. Stand.");
    }

    /**
     * Sends the selected message to the client associated with the Player instance.
     * @param msg the String message to send and display to client
     * @throws IOException 
     */
    public void send(String msg) throws IOException {
        bw.println(msg);
        bw.flush();
    }

    public String receive() throws IOException {
        String msg = br.readLine();
        return msg.trim();
    }

    @Override
    public String query() throws IOException {
        printMenu();
        bw.println("QUERY:Enter a number.");
        String input = receive();
        switch (input) {
            case "1" -> {
                bw.println(hand.displayHand() + " = " + hand.getValue());
                return "NA";
            }
            case "2" -> {
                return "hit";
            }
            case "3" -> {
                return "stand";
            }
            default -> {
                send("Unrecognised command. Please enter only a number");
                return "NA";
            }
        }
    }

    public void closeConn() throws IOException {
        send("CLOSE: Closing game.");
        conn.close();
    }

    public void clearHand() {
        this.hand = new Hand();
    }

    @Override
    public int getValue() {
        return hand.getValue();
    }

    @Override
    public void endGame(boolean win, Hand dealer) throws IOException {
        if (win) {
            bw.println("You win!");
        } else {
            bw.println("You lose...");
        }
        bw.println(String.format("Your hand: %s (Value: %d)", hand.displayHand(), hand.getValue()));
        bw.println(String.format("Dealer's hand: %s (Value: %d)", dealer.displayHand(), dealer.getValue()));
    }

    @Override
    public String toString() {
        return "player " + id;
    }
}
