package org.example;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    // creates scanner object
    private static final Scanner SCANNER_IN = new Scanner(System.in);
    private static boolean isAppRunning = true;
    public static void main(String[] args) {

        // print a menu. DONE
        // get user input for coin type of price to find. DONE
        // use API to find current price of coin
        // parse API response, get the price formatted as currency string
        // print price for user to see
        try {
            while(isAppRunning) {   // will keep application running until they input "exit"
                startMenu();
            }
        } catch (UserRequestsExitException e) { // if they type "exit", this will run.
            System.out.println("GoodBye!");
            // Any logic for normal shutdown for database connections ect...
        }
    }

    private static void startMenu() {
        System.out.println("\n--------------------------------------");
        System.out.println("What coin price would you like to see?\n");
        for (String coinType : CryptoAPI.ALL_COIN_TYPES) { // iterates through the coin types supported
            System.out.println("\t" + coinType);           // and shows the user all coins supported
        }
        System.out.println("(type exit to quit)");
        System.out.print("> ");

        String input = SCANNER_IN.nextLine().toLowerCase(Locale.ROOT); // Reads user input for coin type

        if(input.equals("exit")) {                  // if the user wants to exit, this will allow it
            isAppRunning = false;
            throw new UserRequestsExitException(); // exits the program.
        }

        if (isCoinTypeValid(input)) {               // validates if coins is supported
            // do something
            System.out.println("... checking price for: " + input);
        } else {
            System.out.println("Unsupported Coin Type."); // if coin is invalid print
            startMenu();                            // restarts the menu if coin is invalid
        }
    }

    private static boolean isCoinTypeValid(String input) {
        return CryptoAPI.ALL_COIN_TYPES.contains(input); // if input is in the list, return true, else return false
    }
}
