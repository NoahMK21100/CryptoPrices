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


    // HTTP client to send requests
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient(); // Creates new instance of httpclient
    // Base URL of the CoinCap API
    private static final String COINCAP_ASSESTS_BASE_API = "https://api.coincap.io/v2/assets?";
    // ObjectMapper to parse JSON responses
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final DecimalFormat PRICE_FORMAT = new DecimalFormat("$#,##0.00");   // Added a comma to the price formatting
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

    public CoinData getCoinData(String coinType) {
        try {
            // Construct search query
            String searchQuery = "search=" + coinType;
            String limitQuery = "limit=1";
            URI uri = new URI(COINCAP_ASSESTS_BASE_API
                    + String.join("&", Arrays.asList(searchQuery, limitQuery)));
            // Create HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();

            // Send HTTP request and get response
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse JSON response into CoinDataList object
            String responseBody = response.body();
            CoinDataList coinDataList = OBJECT_MAPPER.readValue(responseBody, CoinDataList.class);

            // Get first coin data from the list
            CoinData coinData = coinDataList.getData().get(0);

            // Format price using PRICE_FORMAT
            String formattedPrice = PRICE_FORMAT.format(Double.parseDouble(coinData.getPriceUsd()));
            coinData.setPriceUsd(formattedPrice);

            return coinData;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CoinData getCoinDataBySymbol(String symbol) {
        try {
            // Construct search query
            String searchQuery = "search=" + symbol;
            String limitQuery = "limit=1";
            URI uri = new URI(COINCAP_ASSESTS_BASE_API
                    + String.join("&", Arrays.asList(searchQuery, limitQuery)));
            // Create HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();

            // Send HTTP request and get response
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse JSON response into CoinDataList object
            String responseBody = response.body();
            CoinDataList coinDataList = OBJECT_MAPPER.readValue(responseBody, CoinDataList.class);

            // Get first coin data from the list
            CoinData coinData = coinDataList.getData().get(0);

            // Format the price using PRICE_FORMAT
            String formattedPrice = PRICE_FORMAT.format(Double.parseDouble(coinData.getPriceUsd()));
            coinData.setPriceUsd(formattedPrice);

            return coinData;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

