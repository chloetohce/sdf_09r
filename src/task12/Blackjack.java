package task12;

import java.util.*;
import task11.*;

public class Blackjack {
    private static final int BUST = 21;
    private final List<Player> players;
    private final Dealer dealer;
    private Stack<Person> queue;
    private List<Person> standing;
    private Deck deck;

    public Blackjack(Dealer dealer, List<Player> players) {
        this.queue = new Stack<>();
        this.queue.addAll(players);
        this.queue.add(dealer);
        this.deck = new Deck();
        this.players = players;
        this.dealer = dealer;
        this.standing = new ArrayList<>();
    }

    public void start() throws InterruptedException {
        deck.shuffle();
        for (int i = 0; i < 2; i++) {
            for (Person p : queue) {
                p.hit(deck.deal());
                System.out.printf("GAME: Dealt %s a card.\n", p.toString());
                System.out.println();
                Thread.sleep(500);
            }
        }
        play();
    }

    public void play() {
        while (!queue.isEmpty()) {
            Person curr = queue.pop();
            String response = curr.query();
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

    public void end() {
        for (Person p : players) {
            if ((p.getValue() > dealer.getValue() && p.getValue() <= BUST) || dealer.getValue() > BUST) {
                p.endGame(true, dealer.getHand());
            } else {
                p.endGame(false, dealer.getHand());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Player player = new Player();
        Dealer dealer = new Dealer();
        Blackjack game = new Blackjack(dealer, List.of(player));
        game.start();
    }
}
