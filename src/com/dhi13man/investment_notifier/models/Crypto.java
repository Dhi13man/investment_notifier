package com.dhi13man.investment_notifier.models;

/**
 * Data Model storing Cryptocurrency data.
 */
public class Crypto {
    /**
     * The Code of the Cryptocurrency
     */
    public String code;

    /**
     * The Name of the Cryptocurrency
     */
    public String name;

    /**
     * The price below which script will send Notification
     */
    public double priceLowerThreshold;

    /**
     * The price above which script will send Notification
     */
    public double priceUpperThreshold;

    /**
     * The constructor of the Data Model storing Cryptocurrency data.
     * @param cryptoCode The Code of the Cryptocurrency.
     * @param cryptoName The Name of the Cryptocurrency.
     * @param cryptoPriceLowerThreshold The script will send Notification if the price of the Crypto is below this.
     * @param cryptoPriceUpperThreshold The script will send Notification if the price of the Crypto is above this.
     */
    public Crypto(
            String cryptoCode,
            String cryptoName,
            double cryptoPriceLowerThreshold,
            double cryptoPriceUpperThreshold
    ) {
        code = cryptoCode;
        name = cryptoName;
        priceLowerThreshold = cryptoPriceLowerThreshold;
        priceUpperThreshold = cryptoPriceUpperThreshold;
    }
}
