package task11;

import java.util.*;

public class Deck {
    private enum Suits {DIAMOND, CLUBS, HEART, SPADE};
    private enum Values {
        VAL1("1"),
        VAL2("2"),
        VAL3("3"),
        VAL4("4"),
        VAL5("5"),
        VAL6("6"),
        VAL7("7"),
        VAL8("8"),
        VAL9("9"),
        VALJ("J"),
        VALQ("Q"),
        VALK("K");
        
        private final String id;
        
        private Values(String id) {
            this.id = id;
        }

        public String id() {
            return id;
        }
    }
    private List<Card> deck;

    public Deck() {
        this.deck = new ArrayList<>();
        for (Suits suit : Suits.values()) {
            for (Values val : Values.values()) {
                Card c = new Card(suit.toString(), val.id());
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

    public static void main(String[] args) {
        Deck d = new Deck();
        d.display();
        System.out.println();
        d.shuffle();
        d.display();
    }

}
