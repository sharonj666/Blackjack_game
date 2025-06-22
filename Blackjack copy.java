package edu.nyu.cs.assignment3;

import java.util.Random;
import java.util.Scanner;

public class Blackjack {

    /**
     * A program to play blackjack.
     * 
     */

    /* Do not modify this method */
    public static Random initRandom(String[] args) {
        if (args.length >= 1) {
            return new Random(Long.parseLong(args[0]));
        } else {
            return new Random();
        }
    }

    public static int Card(Random r) {
        return r.nextInt(10) + 2; // Card value should range from 2 to 11
    }

    public static void Winner(int playerTotal, int dealerTotal, int[] playerCards, int[] dealerCards) {
        if (playerTotal > 21) {
            System.out.print("The dealer's cards are: ");
            printCards(dealerCards);
            System.out.println("Dealer wins!"); // Player busts, dealer wins
        } else {
            System.out.print("Your cards are: ");
            printCards(playerCards);
            // Only print the dealer's cards once when declaring the winner
            System.out.print("The dealer's cards are: ");
            printCards(dealerCards);

            if (dealerTotal > 21) {
                System.out.println("You win!"); // Dealer busts, player wins
            } else if (playerTotal > dealerTotal) {
                System.out.println("You win!"); // Player wins
            } else if (dealerTotal > playerTotal) {
                System.out.println("Dealer wins!"); // Dealer wins
            } else {
                System.out.println("Tie!"); // Tie
            }
        }
    }

    // Method to print cards
    public static void printCards(int[] cards) {
        boolean first = true;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] != 0) {
                if (!first) System.out.print(", ");
                System.out.print(cards[i]);
                first = false;
            }
        }
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        Random r = initRandom(args); // Do not modify this line
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Blackjack!");
        

        
        // Accumulating the player cards' total value for the two initial cards
        int[] playerCards = new int[10]; // Initializing the player card array
        int playertotal = 0;
        // Print out initial 2 cards
        for (int i = 0; i < 2; i++) {
            int cardValue = Card(r);
            playertotal += cardValue;
            playerCards[i] = cardValue;
        }
        System.out.print("Your cards are: ");
        
        
        // Documenting the dealer's initial 2 cards value
        int dealertotal = 0;
        int[] dealerCards = new int[10]; // Initializing the dealer card array
        for (int i = 0; i < 2; i++) {
            int cardValue = Card(r);
            dealertotal += cardValue;
            dealerCards[i] = cardValue;
        }
        
        
        printCards(playerCards);

        // Call the player total by the player turn
        playertotal = PlayerTurn(r, scanner, playerCards, playertotal);

         

        // Handle the dealer turn
        int dealerTotal = 0;
        // Dealer only plays when the player didn't bust
        if (playertotal <= 21) {
            dealerTotal = DealerTurn(r, dealerCards, dealertotal);
        } else {
            dealerTotal = dealertotal;
        }

        Winner(playertotal, dealerTotal, playerCards, dealerCards);
        scanner.close();
    }

    // Accumulating the player cards' total value for other rounds hit
    public static int PlayerTurn(Random r, Scanner scanner, int[] playerCards, int playertotal) {
        while (true) {
            System.out.println("Would you like to hit or stand? ");
            String choice = scanner.nextLine().toLowerCase();
            if (choice.equals("stand")) {
                break;
            } else {
                // Find the first empty spot in the array and add a new card
                for (int i = 2; i < playerCards.length; i++) {
                    if (playerCards[i] == 0 && playertotal<=21) { // Empty slot found
                        int cardValue = Card(r);
                        playerCards[i] = cardValue;
                        playertotal += cardValue;
                        if (playertotal<=21) {
                        	System.out.print("Your cards are: ");
                            printCards(playerCards);
                        }
                        else if (playertotal > 21) {
                        	System.out.println("You have bust!");
                        	System.out.print("Your cards are: ");
                            printCards(playerCards);
                            return playertotal; 
                        }
                        break;
                    }
                   
                }
            }
        
        }
        return playertotal;
    }

    // Accumulating dealer's total card value
    public static int DealerTurn(Random r, int[] dealerCards, int dealertotal) {
        while (true) {
            if (dealertotal > 21) {
                System.out.println("The dealer has bust!");
                break;
            }
            // 50% chance to hit or stand
            if (r.nextBoolean()) {
                System.out.println("The dealer hits.");
                // Find the first empty spot in the array and add a new card
                for (int i = 0; i < dealerCards.length; i++) {
                    if (dealerCards[i] == 0) { // Empty slot found
                        int card = Card(r);
                        dealerCards[i] = card;
                        dealertotal += card;
                        break;
                    }
                }
            } else {
                System.out.println("The dealer stands.");
                break;
            }
        }
        return dealertotal;
    }
}//end of class