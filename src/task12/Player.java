package task12;

import java.io.Console;
import task11.card.*;

public class Player implements Person{
    private final Hand hand;

    public Player() {
        this.hand = new Hand();
    }

    @Override
    public void hit(Card c) {
        hand.add(c);
    }

    public void printMenu() {
        System.out.println("What would you like to do?");
        System.out.println("1: Display current hand.");
        System.out.println("2: Get another card.");
        System.out.println("3: Stand.");
        System.out.println();
    }

    @Override
    public String query() {
        Console cons = System.console();
        printMenu();
        String input = cons.readLine("> ").trim();
        switch (input) {
            case "1" -> {
                System.out.println(hand.displayHand());
                System.out.println("Current hand value: " + hand.getValue());
                System.out.println();
                return "NA";
            }
            case "2" -> {
                return "hit";
            }
            case "3" -> {
                return "stand";
            }
            default -> {
                System.out.println("Unrecognised command. Please enter only a number");
                return "NA";
            }
        }
    }

    @Override
    public int getValue() {
        return hand.getValue();
    }

    @Override
    public void endGame(boolean win, Hand dealer) {
        if (win) {
            System.out.println("You win!");
        } else {
            System.out.println("You lose...");
        }
        System.out.printf("Your hand: %s (Value: %d)\n", hand.displayHand(), hand.getValue());
        System.out.printf("Dealer's hand: %s (Value: %d)\n", dealer.displayHand(), dealer.getValue());
        System.out.println();
    }

    @Override
    public String toString() {
        return "player";
    }

}
