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
    public double priceUpperThreshold;

    /**
     * The price below which script will send Notification
     */
    public double priceLowerThreshold;

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
