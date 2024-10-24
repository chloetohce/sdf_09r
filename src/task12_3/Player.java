package task12_3;

import java.io.*;
import java.net.*;
import task11.card.*;

public class Player implements Person {
    private final Hand hand;
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

    public String printMenu() {
        return "What would you like to do?\n1. Display current hand.\n2. Get another card.\n3. Stand.\n\n";
    }

    /**
     * Sends the selected message to the client associated with the Player instance.
     * @param msg the String message to send and display to client
     * @throws IOException 
     */
    public void send(String msg) throws IOException {
        bw.println(msg);
    }

    public String receive() throws IOException {
        String msg = br.readLine();
        return msg.trim();
    }

    @Override
    public String query() throws IOException {
        bw.println("QUERY:" + printMenu());

        String input = receive();
        System.out.println(input);
        switch (input) {
            case "1" -> {
                bw.println(hand.displayHand() + " = " + hand.getValue());
                // send(hand.displayHand());
                // send("Current hand value: " + hand.getValue());
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

    @Override
    public int getValue() {
        return hand.getValue();
    }

    @Override
    public void endGame(boolean win, Hand dealer) throws IOException {
        // if (win) {
        //     bw.println("You win!");
        // } else {
        //     bw.println("You lose...");
        // }
        bw.println(String.format("Your hand: %s (Value: %d)\nDealer's hand: %s (Value: %d)", hand.displayHand(), 
            hand.getValue(), dealer.displayHand(), dealer.getValue()));
    }

    @Override
    public String toString() {
        return "player " + id;
    }
}
