package task11;

import java.util.*;
import task11.card.*;

public class Deck {

    private List<Card> deck;

    public Deck() {
        this.deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (CardValue val : CardValue.values()) {
                Card c = new Card(suit, val);
                deck.add(c);
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public void display() {
        for (Card c : deck) {
            System.out.println(c);
        }
    }

    public Card deal() {
        return deck.removeFirst();
    }

    public static void main(String[] args) {
        Deck d = new Deck();
        d.display();
        System.out.println();
        d.shuffle();
        d.display();
    }

}
