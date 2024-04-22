package org.example;

import org.example.model.CoinData;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    /*
     - Interacts with user
     - uses CryptoAPI class to retrieve current price.
     - gives the menu for user to select what coin they want info on
     - handles user input and exits when told to
       */
    private static final Scanner SCANNER_IN = new Scanner(System.in);
    private static boolean isAppRunning = true;
    private static final CryptoAPI cryptoApi = new CryptoAPI();

    public static void main(String[] args) {

        try {
            while (isAppRunning) {                               // will keep application running until they input "exit"
                startMenu();
            }
        } catch (UserRequestsExitException e) {                 // if they type "exit", will run.
            System.out.println("GoodBye!");
            // Any logic for future shutdown for database connections ect...
        }
    }

    private static void startMenu() {
        System.out.println("\n--------------------------------------");
        System.out.println("What coin's information would you like to see?\n");
        for (String coinType : CryptoAPI.ALL_COIN_TYPES) {
            System.out.println("\t" + coinType);
        }
        System.out.println("(type exit to quit)");
        System.out.print("> ");

        String input = SCANNER_IN.nextLine().toLowerCase(Locale.ROOT);

        if (input.equals("exit")) {
            isAppRunning = false;
            throw new UserRequestsExitException();
        }

        CoinData coinData = null;
        if (isCoinTypeValid(input)) {
            if (CryptoAPI.ALL_COIN_TYPES.contains(input)) {
                coinData = cryptoApi.getCoinDataBySymbol(input);
            } else {
                coinData = cryptoApi.getCoinDataBySymbol(input); // Call method to get coin data by symbol
            }

            System.out.println("\nCoin Information:");
            System.out.println("ID/Name: " + coinData.getId());
            System.out.println("Symbol: " + coinData.getSymbol());
            System.out.println("Price: " + coinData.getPriceUsd());
            System.out.println("Rank: #x" + coinData.getRank());
            System.out.println("Market Cap (USD): " + coinData.getMarketCapUsd());
            System.out.println("Volume USD 24Hr: " + coinData.getVolumeUsd24Hr());
            System.out.println("Changed Percent 24Hr: " + coinData.getChangePercent24Hr());
        } else {
            System.out.println("Invalid coin type or symbol.");
            startMenu();
        }
    }

    private static boolean isCoinTypeValid(String input) {
        return CryptoAPI.ALL_COIN_TYPES.contains(input) ||
                CryptoAPI.ALL_COIN_TYPES.stream().anyMatch(type -> cryptoApi.getCoinDataBySymbol(type).getSymbol().equalsIgnoreCase(input));
    }
}
