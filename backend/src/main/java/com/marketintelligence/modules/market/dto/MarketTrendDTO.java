package com.marketintelligence.modules.market.dto;

import java.math.BigDecimal;

public class MarketTrendDTO {

    private String month;
    private BigDecimal totalSales;

    public MarketTrendDTO() {
    }

    public MarketTrendDTO(String month, BigDecimal totalSales) {
        this.month = month;
        this.totalSales = totalSales;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }
}