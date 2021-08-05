package com.dhi13man.investment_notifier.interfaces;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.dhi13man.investment_notifier.models.Crypto;
import com.dhi13man.investment_notifier.models.Stock;

/**
 * The Scheduled looping task that is the application's process flow.
 */
class InvestmentTrackTask extends TimerTask {
    private final NetworkRequestInterface requestInterface;

    private final String currency;
    private final List<Crypto> cryptoList;
    private final List<Stock> stockList;

    /**
     * Constructor
     * @param currencyCode Code of the currency where Crypto prices will be converted to. Eg. USD, INR. Stock price is only available in USD!
     * @param cryptos List of Cryptocurrencies that need to be tracked.
     * @param stocks List of Stocks that need to be tracked.
     */
    public InvestmentTrackTask(String currencyCode, List<Crypto> cryptos, List<Stock> stocks) {
        // Set up Network and Notification Interface for requests
        requestInterface = new NetworkRequestInterface();
        // Initialize Class Members
        currency = currencyCode;
        cryptoList = cryptos;
        stockList = stocks;
    }

    @Override
    public void run() {
        for (Crypto crypto : cryptoList) {
            try {
                double price = getCryptoPrice(crypto.code);
                if (price == -1)
                    NotificationInterface.sendNotification(
                            "Request Failed",
                            "Kindly Run the program with proper instructions! Failed Crypto Code: " + crypto.code,
                            ""
                    );
                else if (price > crypto.priceUpperThreshold || price < crypto.priceLowerThreshold) {
                    final String alertType = price > crypto.priceUpperThreshold ? " Rise Alert!" : " Fall Alert!";
                    NotificationInterface.sendNotification(
                            crypto.name.toUpperCase() + alertType,
                            crypto.name + " Price is " + price + " " + currency.toUpperCase(),
                            "https://static.coingecko.com/s/coingecko-logo-d13d6bcceddbb003f146b33c2f7e8193d72b93bb343d38e392897c3df3e78bdd.png"
                            );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Stock stock : stockList) {
            try {
                double price = getStockPrice(stock.code);
                if (price == -1)
                    NotificationInterface.sendNotification(
                            "Request Failed",
                            "Kindly Run the program with proper instructions! Failed Stock Code: " + stock.code,
                            ""
                    );
                else if (price > stock.priceUpperThreshold || price < stock.priceLowerThreshold) {
                    final String alertType = price > stock.priceUpperThreshold ? " Rise Alert!" : " Fall Alert!";
                    NotificationInterface.sendNotification(
                            stock.name.toUpperCase() + alertType,
                            stock.name + " Price is " + price + " USD",
                            ""
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Highly abstracted method to easily get CryptoCurrency price from CoinGecko API.
     *
     * Uses the CoinGecko Free API (59 requests per minute)
     * For API information: https://www.coingecko.com/en/api/
     * @param cryptoCode The code of the Cryptocurrency whose price is required.
     * @return price of the Crypto in the given currency.
     */
    private double getCryptoPrice(String cryptoCode) {
        // https://api.coingecko.com/api/v3/simple/price?ids=ethereum&vs_currencies=inr
        String endpointURL = String.format(
                "https://api.coingecko.com/api/v3/simple/price?ids=%s&vs_currencies=%s",
                cryptoCode,
                currency
        );
        try {
            String out = requestInterface.requestGET(endpointURL);
            String suffix = out.split(String.format("\\{\"%s\":\\{\"%s\":", cryptoCode, currency), 2)[1];
            String value = suffix.split("}}", 2)[0];
            return Double.parseDouble(value);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Highly abstracted method to easily get Stock price from AlphaVantage API.
     *
     * Uses the AlphaVantage Free API (5 Requests per minute and 500 Requests per day.)
     * For API information: https://www.alphavantage.co/documentation/
     * @param stockCode The code of the Cryptocurrency whose price is required.
     * @return price of the Crypto in USD!
     */
    public double getStockPrice(String stockCode) {
        final String apiKey = "ALPHA_VANTAGE_API_KEY";
        String endpointURL = String.format(
                "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=%s&apikey=%s",
                stockCode,
                apiKey
        );
        try {
            String out = requestInterface.requestGET(endpointURL);
            String suffix = out.split("\"05. price\": \"", 2)[1];
            String valueUSD = suffix.split("\"", 2)[0];
            return Double.parseDouble(valueUSD);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}

/**
 * Internally handles the entire scheduled process flow of the application using every relevant interface.
 */
public class InvestmentTrackInterface {
    /**
     * Constructor
     * @param currencyCode Code of the currency where Crypto prices will be converted to. Eg. USD, INR. Stock price is only available in USD!
     * @param cryptos List of Cryptocurrencies that need to be tracked.
     * @param stocks List of Stocks that need to be tracked.
     */
    public InvestmentTrackInterface(String currencyCode, List<Crypto> cryptos, List<Stock> stocks, long refreshPeriod) {
        Timer timer = new Timer();
        InvestmentTrackTask task = new InvestmentTrackTask(currencyCode, cryptos, stocks);
        timer.scheduleAtFixedRate(task, 1000, refreshPeriod);
    }
}