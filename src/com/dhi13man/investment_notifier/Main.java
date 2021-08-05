package com.dhi13man.investment_notifier;

import com.dhi13man.investment_notifier.interfaces.InvestmentTrackInterface;
import com.dhi13man.investment_notifier.models.Crypto;
import com.dhi13man.investment_notifier.models.Stock;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Make Lists of Cryptos and Stocks that you would like to be notified about.
        List<Crypto> cryptos = new ArrayList<>();
        List<Stock> stocks = new ArrayList<>();
        // If not enough command line parameters
        if (args.length < 4) {
            // Add your Cryptos and Stocks here for manual usage.
            cryptos.add(
                new Crypto(
                    "ethereum",
                    "Ethereum",
                    180000,
                    220000
                )
            );
            stocks.add(
                new Stock("SBI", "SBI", 8, 10)
            );
        }
        else processCommandLineParameters(args, cryptos, stocks);

        // Create the interface that schedules checking and notification of prices (process flow of the app)
        new InvestmentTrackInterface("inr", cryptos, stocks, 60000);
    }

    /**
     * Processes the Command Line arguments to process Stocks and Cryptos
     * @param args Command line arguments
     * @param cryptos Crypto List that the application will us
     * @param stocks Stock list that the application will use
     */
    private static void processCommandLineParameters(String[] args, List<Crypto> cryptos, List<Stock> stocks) {
        for (int i = 0; i < args.length; i += 4) {
            if (args[i].startsWith("c_"))
                cryptos.add(
                    new Crypto(
                        args[i].substring(2),
                        args[i + 1],
                        Double.parseDouble(args[i + 2]),
                        Double.parseDouble(args[i + 3])
                    )
                );
            else if (args[i].startsWith("s_"))
                stocks.add(
                    new Stock(
                        args[i].substring(2),
                        args[i + 1],
                        Double.parseDouble(args[i + 2]),
                        Double.parseDouble(args[i + 3])
                    )
                );
            else {
                final Logger logger = Logger.getLogger(Main.class.getName());
                logger.log(Level.SEVERE, "Invalid Command Line Parameters. Every (4 * i + 1)th element must start with c_ or s_.");
                logger.log(Level.SEVERE, "Refer to README.MD");
            }
        }
    }
}
