package task12_3;

import java.util.*;
import task11.card.*;

public class Hand {
    private final List<Card> hand;

    public Hand() {
        this.hand = new ArrayList<>();
    }

    public void add(Card c) {
        hand.add(c);
    }

    public int getValue() {
        int val1 = 0;
        int val2 = 0;
        for (Card c : hand) {
            val1 += c.getVal();
            if (c.getVal() == 1) {
                val2 += 11;
                continue;
            }
            val2 += c.getVal();
        }
        return Math.max(val1, val2) <= 21 ? Math.max(val1, val2) : Math.min(val1, val2);
    }

    public String displayHand() {
        String out = "[";
        for (Card c : hand) {
            out += c.toString() + ", ";
        }
        out = out.substring(0, out.length() - 2) + "]";
        return out;
    }
}
