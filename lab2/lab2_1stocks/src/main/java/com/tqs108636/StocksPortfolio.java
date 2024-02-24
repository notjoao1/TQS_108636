package com.tqs108636;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {
    private IStockMarketService stockmarket;
    private List<Stock> stocks;

    StocksPortfolio(IStockMarketService stockmarket) {
        this.stockmarket = stockmarket;
        this.stocks = new ArrayList<>();
    }

    public void addStock(Stock s) {
        stocks.add(s);
    }

    public double totalValue() {
        return stocks.stream().mapToDouble((s) -> stockmarket.lookUpPrice(s.getLabel()) * s.getQuantity()).sum();
    }
}
