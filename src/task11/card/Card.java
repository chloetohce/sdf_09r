package task11.card;

public class Card {
    private final Suit suit;
    private final CardValue val;
    
    public Card(Suit suit, CardValue value) {
        this.suit = suit;
        this.val = value;
    }

    public String getSuit() {
        return suit.toString();
    }

    public int getVal() {
        return val.getValue();
    }

    @Override
    public String toString() {
        return val + " " + suit;
    }
}
