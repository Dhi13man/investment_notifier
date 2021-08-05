package com.dhi13man.investment_notifier.models;

/**
 * Data Model storing Cryptocurrency data.
 */
public class Crypto {
    private final String code;
    /**
     * @return The Code of the Cryptocurrency
     */
    public String getCode() { return code; }

    private final String name;
    /**
     * @return the Name of the Cryptocurrency
     */
    public String getName() { return name; }

    private final double priceLowerThreshold;
    /**
     * @return the price below which script will send Notification
     */
    public double getPriceLowerThreshold() { return priceLowerThreshold; }

    private final double priceUpperThreshold;
    /**
     * @return the price above which script will send Notification
     */
    public double getPriceUpperThreshold() { return priceUpperThreshold; }

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
