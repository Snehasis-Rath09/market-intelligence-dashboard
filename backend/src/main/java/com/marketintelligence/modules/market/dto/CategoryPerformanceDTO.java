package com.marketintelligence.modules.market.dto;

import java.math.BigDecimal;

public class CategoryPerformanceDTO {

    private String category;
    private BigDecimal totalSales;
    private BigDecimal totalProfit;
    private BigDecimal profitMargin;
    private Long unitsSold;

    public CategoryPerformanceDTO() {
    }

    public CategoryPerformanceDTO(String category, BigDecimal totalSales,
                                   BigDecimal totalProfit, Long unitsSold) {
        this.category = category;
        this.totalSales = totalSales;
        this.totalProfit = totalProfit;
        this.unitsSold = unitsSold;
        if (totalSales != null && totalSales.compareTo(BigDecimal.ZERO) > 0) {
            this.profitMargin = totalProfit
                    .divide(totalSales, 4, java.math.RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        } else {
            this.profitMargin = BigDecimal.ZERO;
        }
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public BigDecimal getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(BigDecimal profitMargin) {
        this.profitMargin = profitMargin;
    }

    public Long getUnitsSold() {
        return unitsSold;
    }

    public void setUnitsSold(Long unitsSold) {
        this.unitsSold = unitsSold;
    }
}