package task10;

import java.io.Console;
import java.security.SecureRandom;
import java.util.*;

public class HigherLower {
    private static final int UPPER_BOUND = 101;
    private static final int LOWER_BOUND = 1;
    private static final int NUM_CARDS = 10;
    private List<Integer> deck;
    

    public HigherLower() {
        this.deck = new LinkedList<>();
        Random rand = new SecureRandom();
        while (deck.size() != NUM_CARDS) {
            int card = rand.nextInt(LOWER_BOUND, UPPER_BOUND);
            if (deck.contains(card)) {continue;}
            deck.add(card);
        }
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public String hint(int card) {
        // Gives how many cards in the deck that are higher or lower than the card specified
        int higher = 0;
        int lower = 0;
        for (int i : deck) {
            if (i < card) {lower++;}
            else {higher++;}
        }
        return String.format("[Higher: %d || Lower: %d]", higher, lower);
    }

    public int deal() {
        int card = deck.getFirst();
        deck.remove(0);
        return card;
    }

    public boolean isEmpty() {
        return deck.isEmpty();
    }

    public static void main(String[] args) throws InterruptedException {
        String input;
        Console cons = System.console();
        System.out.println("Starting game...");
        HigherLower game = new HigherLower();
        game.shuffle();
        
        int card = game.deal();
        do {
            System.out.println("Current card: " + card);
            System.out.println(game.hint(card));
            input = cons.readLine("H/L?  ");

            input = input.trim().toUpperCase();
            int nextCard = game.deal();

            System.out.println("Next card: " + nextCard);

            if ((nextCard > card && input.equals("H")) || (nextCard<card && input.equals("L"))) {
                System.out.println("You got it!");
            } else {
                System.out.println("Oops...");
            }
            System.out.println();
            
            card = nextCard;

            Thread.sleep(250);
            
        } while (!input.equals("QUIT") && !game.isEmpty());
    }
    
}