package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.CoinData;
import org.example.model.CoinDataList;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;


public class CryptoAPI {
    /*
    - Methods to interact with CoinCap API to fetch data
    - Method to display current price of chosen coin (getCurrentCoinPrice)
    - HTTP requests to Coin Cap API, parses JSON response, & extracts the coin price
    -handles exceptions that can occur during requests
     */

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient(); // Creates new instance of httpclient
    private static final String COINCAP_ASSESTS_BASE_API = "https://api.coincap.io/v2/assets?";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final DecimalFormat PRICE_FORMAT = new DecimalFormat("$0,000.00");
    public static final List<String> ALL_COIN_TYPES = Arrays.asList(
            "bitcoin",
            "ethereum",
            "tether",
            "binance-coin",
            "solana",
            "usd-coin",
            "xrp",
            "dogecoin",
            "cardano",
            "shiba-inu",
            "avalanche",
            "wrapped-bitcoin",
            "bitcoin-cash",
            "polkadot",
            "tron",
            "chainlink",
            "near-protocol",
            "polygon",
            "internet-computer",
            "litecoin"
    );

    public String getCurrentCoinPrice(String coinType) {
        // use API to find current price of coin (https://docs.coincap.io/) DONE
        // parse API response,
        // get the price formatted as currency string
        // added MVN dependencies

        try{
            String searchQuery = "search=" + coinType; // Free Requests with imported API to limit information given
            String limitQuery = "limit=1";              // Free Requests with imported API to limit information given
            URI uri = new URI(COINCAP_ASSESTS_BASE_API
                    + String.join("&", Arrays.asList(searchQuery, limitQuery)));
            HttpRequest request = HttpRequest.newBuilder()  // creates new http GET request with URI
                    .uri(uri)
                    .build();

            // sends http request and stores response
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();


            CoinDataList coinDataList = OBJECT_MAPPER.readValue(responseBody, CoinDataList.class); // reads a value of JSON string ( response body ) and converts it into CoinDataList and creates it
            CoinData firstCoin = coinDataList.getData().get(0);
            double priceUsd = Double.parseDouble(firstCoin.getPriceUsd());
            return PRICE_FORMAT.format(priceUsd); // gets

        } catch (Exception e) {
                    // If any exception occurs during the HTTP request, wrap it in a RuntimeException and throw
            throw new RuntimeException(e);
        }
    }
}
