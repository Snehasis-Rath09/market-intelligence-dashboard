package com.marketintelligence.modules.market.dto;

import java.math.BigDecimal;

public class MarketSearchRequestDTO {

    private String keyword;

    private String category;

    private String subCategory;

    private String market;

    private String region;

    private Integer year;

    private BigDecimal minSales;

    private BigDecimal maxSales;

    private BigDecimal minProfit;

    private BigDecimal maxProfit;

    // Generate Getters & Setters

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getMinSales() {
        return minSales;
    }

    public void setMinSales(BigDecimal minSales) {
        this.minSales = minSales;
    }

    public BigDecimal getMaxSales() {
        return maxSales;
    }

    public void setMaxSales(BigDecimal maxSales) {
        this.maxSales = maxSales;
    }

    public BigDecimal getMinProfit() {
        return minProfit;
    }

    public void setMinProfit(BigDecimal minProfit) {
        this.minProfit = minProfit;
    }

    public BigDecimal getMaxProfit() {
        return maxProfit;
    }

    public void setMaxProfit(BigDecimal maxProfit) {
        this.maxProfit = maxProfit;
    }
}