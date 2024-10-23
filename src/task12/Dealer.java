package task12;

import task11.card.*;

public class Dealer implements Person{
    private final Hand hand;
    
    public Dealer() {
        this.hand = new Hand();
    }

    @Override
    public void hit(Card c) {
        hand.add(c);
    }

    @Override
    public String query() {
        int handVal = hand.getValue();
        
        // Is there a smarter way to play this? Can we do a probability/ random check based on the visible cards on the table?
        if (handVal <= 17) {
            return "hit";
        }
        return "stand";
    }

    @Override
    public void endGame(boolean win, Hand dealer) {}

    @Override
    public int getValue() {
        return hand.getValue();
    }

    public Hand getHand() {
        return hand;
    }

    @Override
    public String toString() {
        return "dealer";
    }
}
