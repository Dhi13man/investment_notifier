package com.dhi13man.investment_notifier.models;

/**
 * Data Model storing Stock data.
 */
public class Stock {
    private final String code;
    /**
     * @return the Code of the Stock
     */
    public String getCode() { return code; }

    private final String name;
    /**
     * @return the Name of the Stock
     */
    public String getName() { return name; }

    private final double priceUpperThreshold;
    /**
     * @return the price above which script will send Notification
     */
    public double getPriceUpperThreshold() { return priceUpperThreshold; }

    private final double priceLowerThreshold;
    /**
     * @return the price below which script will send Notification
     */
    public double getPriceLowerThreshold() { return priceLowerThreshold; }

    /**
     * The constructor of the Data Model storing Stock data.
     * @param stockCode The Code of the Stock.
     * @param stockName The Name of the Stock.
     * @param stockPriceLowerThreshold The script will send Notification if the price of the Stock is below this.
     * @param stockPriceUpperThreshold The script will send Notification if the price of the Stock is above this.
     */
    public Stock(String stockCode, String stockName, double stockPriceLowerThreshold, double stockPriceUpperThreshold) {
        code = stockCode;
        name = stockName;
        priceLowerThreshold = stockPriceLowerThreshold;
        priceUpperThreshold = stockPriceUpperThreshold;
    }
}
