package task12_3;

import java.io.IOException;

import task11.card.*;

public interface Person {
    public void hit(Card card);

    public String query() throws IOException;

    public void endGame(boolean win, Hand dealer) throws IOException;

    public int getValue();
}