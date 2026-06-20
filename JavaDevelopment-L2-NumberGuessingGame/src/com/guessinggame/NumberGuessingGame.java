package com.guessinggame;

import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int round = 1;
        String playAgain;

        System.out.println("=================================");
        System.out.println("      NUMBER GUESSING GAME");
        System.out.println("=================================");

        do {

            int maxNumber = 100;
            int maxAttempts = 7;

            System.out.println("\nSelect Difficulty Level");
            System.out.println("1. Easy (1-50, 10 Attempts)");
            System.out.println("2. Medium (1-100, 7 Attempts)");
            System.out.println("3. Hard (1-200, 5 Attempts)");
            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    maxNumber = 50;
                    maxAttempts = 10;
                    break;

                case 2:
                    maxNumber = 100;
                    maxAttempts = 7;
                    break;

                case 3:
                    maxNumber = 200;
                    maxAttempts = 5;
                    break;

                default:
                    System.out.println("Invalid Choice!");
                    System.out.println("Medium Level Selected.");
            }

            int secretNumber = random.nextInt(maxNumber) + 1;

            int attempts = 0;
            boolean guessed = false;

            System.out.println("\nRound " + round);
            System.out.println("Guess the number between 1 and " + maxNumber);

            while (attempts < maxAttempts) {

                System.out.println("\nAttempts Left: "
                        + (maxAttempts - attempts));

                System.out.print("Enter Your Guess: ");
                int guess = sc.nextInt();

                attempts++;

                if (guess == secretNumber) {

                    System.out.println("\nCorrect! You guessed the number.");
                    System.out.println("Attempts Used: " + attempts);

                    guessed = true;
                    break;
                }

                else if (guess < secretNumber) {
                    System.out.println("Too Low!");
                }

                else {
                    System.out.println("Too High!");
                }
            }

            if (!guessed) {

                System.out.println("\nYou Lost!");
                System.out.println("The Correct Number Was: "
                        + secretNumber);
            }

            System.out.println("\n===== ROUND SUMMARY =====");
            if (guessed) {
                System.out.println("Round " + round
                        + " - Guessed in "
                        + attempts + " attempts");
            } else {
                System.out.println("Round " + round
                        + " - Failed to Guess");
            }

            round++;

            System.out.print("\nPlay Again? (yes/no): ");
            playAgain = sc.next();

        } while (playAgain.equalsIgnoreCase("yes"));

        System.out.println("\nThank You For Playing!");
        sc.close();
    }
}