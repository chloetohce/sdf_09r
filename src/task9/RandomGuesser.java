package task9;

import java.io.*;
import java.security.SecureRandom;
import java.util.*;

public class RandomGuesser {
    private final int num;
    private final File f;
    private List<Integer> guesses;

    public RandomGuesser(File f) {
        Random rand = new SecureRandom();
        num = rand.nextInt(100000, 999999);
        this.f = f;
        this.guesses = new ArrayList<>();
        try {
            f.delete();
            f.createNewFile();
            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(Integer.toString(num));
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException e) {
            System.err.println("Error creating new save file. Please restart and try again.");
            System.exit(-1);
        }
    }

    private RandomGuesser(File f, int num, List<Integer> guesses) {
        this.num = num;
        if (!f.exists()) {
            System.out.println("File provided does not exist. Creating new file.");
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.err.println("Error creating new save file. Please restart and try again.");
            }
        }
        this.f = f;
        this.guesses = guesses;
    }

    private static RandomGuesser load(File f) {
        try (FileReader fr = new FileReader(f); BufferedReader br = new BufferedReader(fr);) {
            int num = Integer.parseInt(br.readLine());
            String line = "";
            List<Integer> guesses = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                guesses.add(Integer.parseInt(line));
            }
            br.close();
            return new RandomGuesser(f, num, guesses);
        } catch (IOException e) {
            System.err.println("Error reading save file. Starting a new game...");
            return new RandomGuesser(f);
        }
    }

    private void saveGuess(int guess) {
        try (FileWriter fw = new FileWriter(f); BufferedWriter bw = new BufferedWriter(fw);) {
            bw.append(Integer.toString(guess));
            bw.close();
        } catch (IOException e) {
            System.out.println("Guess was not saved to file.");
        }
    }

    private void handleGuess(int guess) {
        if (guesses.contains(guess)) {
            System.out.println("You've already guessed that number, silly!");
        } else if (guess == num) {
            System.out.println("You got it!");
        } else if (guess < num) {
            System.out.println("HIGHER!");
        } else if (guess > num) {
            System.out.println("LOWER!");
        } else {
            System.out.println("Something went wrong.");
        }
    }

    public void play() {
        System.out.println("Guess what number I'm currently thinking of!");
        System.out.println("The number is between 100000 and 999999.");
        System.out.println();
        System.out.println("If you ever need to leave the game, type 'quit' to exit current game.");
        System.out.println();

        Console cons = System.console();
        String input;
        do {
            input = cons.readLine("Enter guess: ").trim();
            if (!input.matches("[0-9]+")) {
                System.out.println("Please only enter numbers.");
                System.out.println();
                continue;
            } else if (input.length() != 6) {
                System.out.println("Our guessing range is only between 100000 and 999999. Please only enter 6 digits.");
                System.out.println();
                continue;
            }
            int guess = Integer.parseInt(input.trim());
            guesses.add(guess);
            saveGuess(guess);
            handleGuess(guess);
        } while (!input.equals("quit") && !input.equals(Integer.toString(num)));
        System.out.println("Exiting game...");
    }

    public static void printMenu() {
        System.out.println("======================== MENU ========================");
        System.out.println("new: start new game.");
        System.out.println("load <file>: load specified file from game database.");
        System.out.println("quit: quit program.");
        System.out.println("======================================================");
        System.out.println();
    }
    public static void main(String[] args) throws InterruptedException {
        Console cons = System.console();
        String input;
        do { 
            printMenu();
            input = cons.readLine("> ");
            if (input.equals("new")) {
                System.out.println("Enter file to save game data.");
                input = cons.readLine("> ");
                File f = new File("db" + File.separator + "RandomGuesser" + File.separator + input);
                RandomGuesser game = new RandomGuesser(f);
                game.play();
                
            } else if (input.startsWith("load")) {
                String fName = input.substring(5);
                File f = new File("db" + File.separator + "RandomGuesser" + File.separator + fName);
                RandomGuesser game = RandomGuesser.load(f);
            }
        } while (!input.equals("quit"));
        System.out.println("Exiting program...");
        Thread.sleep(50);
        System.out.println("Goodbye!");
    }

}