package task12_3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;
import task11.*;

public class BlackjackServer {
    private static final int BUST = 21;
    private final List<Player> players;
    private final Dealer dealer;
    private Queue<Person> queue;
    private List<Person> standing;
    private Deck deck;
    private ServerSocket socket;

    public BlackjackServer(List<Player> players, ServerSocket socket) {
        this.queue = new LinkedList<>();
        this.queue.addAll(players);
        this.dealer = new Dealer();
        this.queue.add(dealer);
        this.deck = new Deck();
        this.players = players;
        this.standing = new ArrayList<>();
        this.socket = socket;
    }

    // private static void stopSocket(Socket socket) throws IOException {
    //     long startTime = System.currentTimeMillis();
    //     while (System.currentTimeMillis() - startTime < 2000) {
    //         System.out.println("waiting...");
    //     }
    //     socket.close();
    // }

    public void start() throws IOException, InterruptedException {
        deck.shuffle();
        for (int i = 0; i < 2; i++) {
            for (Person p : queue) {
                p.hit(deck.deal());
                String msg = String.format("GAME:Dealt %s a card.", p.toString());;
                System.out.println(msg);
                for (Player temp : players) {
                    temp.send(msg);
                }
                Thread.sleep(250);
            }
        }
        play();
    }

    public void play() throws IOException, InterruptedException {
        while (!queue.isEmpty()) {
            Person curr = queue.poll();
            String response = curr.query();
            System.out.println(curr.toString() + ": " + response);
            while (response.equals("NA")) {
                response = curr.query();
            }

            if (response.equals("hit")) {
                curr.hit(deck.deal());
                queue.add(curr);
            }
            else {
                standing.add(curr);
            }
        }

        end();
    }

    public void end() throws IOException, InterruptedException {
        for (Person p : players) {
            if ((p.getValue() > dealer.getValue() && p.getValue() <= BUST) || dealer.getValue() > BUST) {
                p.endGame(true, dealer.getHand());
            } else {
                p.endGame(false, dealer.getHand());
            }
        }
        restart();
    }

    public void restart() throws IOException, InterruptedException {
        List<Player> newPlayers = new ArrayList<>();
        for (Player p : players) {
            p.clearHand();
            p.send("QUERY:Would you like to play again? Y/N");
            String msg = p.receive();

            if (msg.equals("Y")) {
                newPlayers.add(p);
            } else {
                p.closeConn();
            }
        }
        if (newPlayers.isEmpty()) socket.close();
        else {
            BlackjackServer game = new BlackjackServer(newPlayers, socket);
            game.start();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 3000;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        ServerSocket server = new ServerSocket(port);
        server.setSoTimeout(15000);
        System.out.println("Starting blackjack server...");
        List<Player> players = new ArrayList<>();
        try {
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < 15000) {
                Socket conn = server.accept();
                Player player = new Player(conn);
                players.add(player);
                System.out.println(player.toString() + " connected");
            }
        } catch (SocketTimeoutException e) {
            System.out.println("No connection found. Shutting down...");
            server.close();
        }
        
        BlackjackServer game = new BlackjackServer(players, server);
        game.start();
        
        
    }
}
