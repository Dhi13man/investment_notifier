package com.dhi13man.investment_notifier;

import com.dhi13man.investment_notifier.models.Crypto;
import com.dhi13man.investment_notifier.models.Stock;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        List<Crypto> cryptos = Arrays.asList(
                new Crypto("ethereum", "Ethereum", 2000000)
        );
        List<Stock> stocks = Arrays.asList(
                new Stock("ethereum", "Ethereum", 2000000)
        );

        InvestmentTrackInterface investmentTracker = new InvestmentTrackInterface("inr", cryptos, stocks);
    }
}
