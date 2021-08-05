package com.dhi13man.investment_notifier.models;

/**
 * Data Model storing Stock data.
 */
public class Stock {
    /**
     * The Code of the Stock
     */
    public String code;

    /**
     * The Name of the Stock
     */
    public String name;

    /**
     * The price above which script will send Notification
     */
    public double priceThreshold;

    /**
     * The constructor of the Data Model storing Stock data.
     * @param stockCode The Code of the Stock.
     * @param stockName The Name of the Stock.
     * @param stockPriceThreshold The script will send Notification if the price of the Stock is above this.
     */
    public Stock(String stockCode, String stockName, double stockPriceThreshold) {
        code = stockCode;
        name = stockName;
        priceThreshold = stockPriceThreshold;
    }
}
