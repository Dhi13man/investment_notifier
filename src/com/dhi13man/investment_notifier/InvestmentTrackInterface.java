package com.dhi13man.investment_notifier;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.dhi13man.investment_notifier.models.Crypto;
import com.dhi13man.investment_notifier.models.Stock;

class InvestmentTrackTask extends TimerTask {
    private final NetworkRequestInterface requestInterface;

    private final String currency;
    private final List<Crypto> cryptoList;
    private final List<Stock> stockList;

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
                double price = getCryptoPrice(crypto.code, currency);
                if (price > crypto.priceThreshold) {
                    NotificationInterface.sendNotification(
                            crypto.name.toUpperCase() + " Alert!",
                            crypto.name + " Price is " + price,
                            "https://static.coingecko.com/s/coingecko-logo-d13d6bcceddbb003f146b33c2f7e8193d72b93bb343d38e392897c3df3e78bdd.png"
                            );
                }
            } catch (AWTException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Highly abstracted method to easily get Etherium CryptoCurrency price from CoinGecko API.
     *
     * Uses the CoinGecko Free API (59 requests per minute)
     * For API information: https://www.coingecko.com/en/api/
     * @param cryptoCode The code of the Cryptocurrency whose price is required.
     * @param currencyCode The code of the currency in which price is required.
     * @throws IOException as it uses Network request.
     * @return price of the Crypto in the given currency.
     */
    private double getCryptoPrice(String cryptoCode, String currencyCode) throws IOException {
        // https://api.coingecko.com/api/v3/simple/price?ids=ethereum&vs_currencies=inr
        String endpointURL = String.format(
                "https://api.coingecko.com/api/v3/simple/price?ids=%s&vs_currencies=%s",
                cryptoCode,
                currencyCode
        );
        String out = requestInterface.requestGET(endpointURL);
        String suffix = out.split(String.format("\\{\"%s\":\\{\"%s\":", cryptoCode, currencyCode), 2)[1];
        String value = suffix.split("}}", 2)[0];
        return Double.parseDouble(value);
    }
}

/**
 * Internally handles the entire scheduled process flow of the application using every interface.
 */
public class InvestmentTrackInterface {
    public InvestmentTrackInterface(String currencyCode, List<Crypto> cryptos, List<Stock> stocks) {
        Timer timer = new Timer();
        InvestmentTrackTask task = new InvestmentTrackTask(currencyCode, cryptos, stocks);
        timer.scheduleAtFixedRate(task, 1000, 10000);
    }
}