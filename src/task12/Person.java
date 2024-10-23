package task12;

import task11.card.*;

public interface Person {
    public void hit(Card card);

    public String query();

    public void endGame(boolean win, Hand dealer);

    public int getValue();
}
