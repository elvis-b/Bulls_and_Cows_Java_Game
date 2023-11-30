package bullscows;

import java.util.*;

public class Main {

    private StringBuilder secretCode; // The secret code to be guessed
    String possibleCharacters; // The characters that can be used in the secret code

    // This method generates the secret code
    public StringBuilder generateCode(int codeLength) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder result = new StringBuilder();
        Random rand = new Random();
        System.out.println("Input the number of possible symbols in the code:");
        int numPossibleCharacters = 0;
        try {
            numPossibleCharacters = scanner.nextInt(); // Get the number of possible characters
        } catch (InputMismatchException e) {
            System.out.println("Error: \"" + scanner.next() + "\" isn't a valid number."); // Handle invalid input
            return null;
        }

        // Check if the number of possible characters is valid
        if (numPossibleCharacters > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return null;
        } else if (codeLength > numPossibleCharacters) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.\n", codeLength, numPossibleCharacters);
            return null;
        } else if (codeLength == 0) {
            System.out.println("Error: the length of the secret code must be greater than 0."); // Handle edge case where code length is 0
            return null;
        } else {
            // Generate the secret code
            List<Character> characters = new ArrayList<>();
            for (int i = 0; i < numPossibleCharacters; i++) {
                if (i < 10) {
                    characters.add((char) ('0' + i));
                } else {
                    characters.add((char) ('a' + i - 10));
                }
            }
            Collections.shuffle(characters);
            for (int i = 0; i < codeLength; i++) {
                result.append(characters.get(i));
            }
            possibleCharacters = numPossibleCharacters <= 10 ? "0-" + (numPossibleCharacters - 1) : "0-9, a-" + (char) (numPossibleCharacters - 11 + 'a');
        }
        secretCode = result;
        System.out.println("The secret is prepared: " + "*".repeat(codeLength) + " (" + possibleCharacters + ").");
        return result;
    }

    // This method allows the user to guess the code
    public void guessTheCode() {
        if (secretCode == null) {
            return; // If the secret code is null, end the game
        }
        int cows = 0;
        int bulls = 0;
        int turn = 1;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Okay, let's start a game!");
        while (true) {
            System.out.println("Turn " + turn + ":");
            String input = scanner.next();
            int length = Math.min(input.length(), secretCode.length());
            for (int i = 0; i < length ; i++) {
                if (input.charAt(i) == secretCode.charAt(i)) {
                    bulls += 1; // Count the number of bulls
                } else if (secretCode.toString().contains(String.valueOf(input.charAt(i)))) {
                    cows += 1; // Count the number of cows
                }
            }
            // Ensure that bulls do not exceed the count of unique characters in the secret code
            bulls = Math.min(bulls, (int) secretCode.chars().distinct().count());
            if (bulls == secretCode.length()) {
                System.out.printf("Grade: %d bull(s)\n", bulls);
                System.out.println("Congratulations! You guessed the secret code.");
                break; // End the game if the code is guessed correctly
            } else {
                System.out.printf("Grade: %d bull(s) and %d cow(s)\n", bulls, cows);
                bulls = 0;
                cows = 0;
                turn++;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter the secret code's length:");
        int codeLength = 0;
        try {
            codeLength = scanner.nextInt(); // Get the length of the secret code
        } catch (InputMismatchException e) {
            System.out.println("Error: \"" + scanner.next() + "\" isn't a valid number."); // Handle invalid input
            return;
        }
        Main main = new Main();
        main.generateCode(codeLength);
        main.guessTheCode();
    }
}