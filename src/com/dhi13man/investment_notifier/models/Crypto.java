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
     * The price above which script will send Notification
     */
    public double priceThreshold;

    /**
     * The constructor of the Data Model storing Cryptocurrency data.
     * @param cryptoCode The Code of the Cryptocurrency.
     * @param cryptoName The Name of the Cryptocurrency.
     * @param cryptoPriceThreshold The script will send Notification if the price of the Crypto is above this.
     */
    public Crypto(String cryptoCode, String cryptoName, double cryptoPriceThreshold) {
        code = cryptoCode;
        name = cryptoName;
        priceThreshold = cryptoPriceThreshold;
    }
}
