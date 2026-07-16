package com.marketintelligence.modules.market.dto;

import java.util.List;

public class MarketSearchResponseDTO {

    private MarketSummaryDTO summary;

    private List<SearchResultDTO> results;

    // Generate Getters & Setters

    public MarketSummaryDTO getSummary() {
        return summary;
    }

    public void setSummary(MarketSummaryDTO summary) {
        this.summary = summary;
    }

    public List<SearchResultDTO> getResults() {
        return results;
    }

    public void setResults(List<SearchResultDTO> results) {
        this.results = results;
    }
}