package com.marketintelligence.modules.market.dto;

import java.math.BigDecimal;

public class RegionalDemandDTO {

    private String region;
    private BigDecimal totalSales;

    public RegionalDemandDTO() {
    }

    public RegionalDemandDTO(String region, BigDecimal totalSales) {
        this.region = region;
        this.totalSales = totalSales;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }
}