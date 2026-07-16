package com.marketintelligence.modules.market.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.marketintelligence.modules.market.dto.BusinessInsightDTO;
import com.marketintelligence.modules.market.dto.CategoryPerformanceDTO;
import com.marketintelligence.modules.market.dto.GeographyDemandDTO;
import com.marketintelligence.modules.market.dto.MarketSearchRequestDTO;
import com.marketintelligence.modules.market.dto.MarketSearchResponseDTO;
import com.marketintelligence.modules.market.dto.MarketSummaryDTO;
import com.marketintelligence.modules.market.dto.MarketTrendDTO;
import com.marketintelligence.modules.market.dto.RegionalDemandDTO;
import com.marketintelligence.modules.market.dto.SearchResultDTO;
import com.marketintelligence.modules.market.entity.SuperstoreRecord;
import com.marketintelligence.modules.market.repository.SuperstoreRepository;
import com.marketintelligence.modules.market.specification.MarketSpecification;

@Service
public class MarketAnalyticsService {

    private final SuperstoreRepository superstoreRepository;

    public MarketAnalyticsService(SuperstoreRepository superstoreRepository) {
        this.superstoreRepository = superstoreRepository;
    }

    public List<CategoryPerformanceDTO> getCategoryPerformance() {
        return superstoreRepository.aggregateByCategory().stream()
                .map(row -> new CategoryPerformanceDTO(
                        row.getCategory(), row.getTotalSales(), row.getTotalProfit(), row.getTotalUnits()))
                .collect(Collectors.toList());
    }

    public List<RegionalDemandDTO> getRegionalDemand() {
        return superstoreRepository.aggregateByRegion().stream()
                .map(row -> new RegionalDemandDTO(row.getRegion(), row.getTotalSales()))
                .collect(Collectors.toList());
    }

    public List<MarketTrendDTO> getMonthlyTrend() {
        return superstoreRepository.aggregateByMonth().stream()
                .map(row -> new MarketTrendDTO(
                        String.format("%d-%02d", ((Number) row[0]).intValue(), ((Number) row[1]).intValue()),
                        new BigDecimal(row[2].toString())))
                .collect(Collectors.toList());
    }

    public List<GeographyDemandDTO> getGeographyDemand(MarketSearchRequestDTO request) {
        return superstoreRepository.findAll(MarketSpecification.search(request)).stream()
                .collect(Collectors.groupingBy(record -> record.getMarket() + "|" + record.getRegion(),
                        Collectors.reducing(BigDecimal.ZERO, SuperstoreRecord::getSales, BigDecimal::add)))
                .entrySet().stream()
                .map(entry -> {
                    String[] location = entry.getKey().split("\\|", 2);
                    return new GeographyDemandDTO(location[0], location[1], entry.getValue());
                })
                .sorted((left, right) -> right.totalSales().compareTo(left.totalSales()))
                .limit(18)
                .toList();
    }
    private List<MarketTrendDTO> toTrendDTO(
        List<Object[]> result) {

    return result.stream()
            .map(row -> new MarketTrendDTO(
                    row[0].toString(),
                    new java.math.BigDecimal(row[1].toString())))
            .toList();
}
public List<MarketTrendDTO> getTopProducts() {
    return toTrendDTO(
            superstoreRepository.getTopProducts());
}

public List<MarketTrendDTO> getTopSubCategories() {
    return toTrendDTO(
            superstoreRepository.getTopSubCategories());
}

public List<MarketTrendDTO> getTopCities() {
    return toTrendDTO(
            superstoreRepository.getTopCities());
}

public List<MarketTrendDTO> getTopStates() {
    return toTrendDTO(
            superstoreRepository.getTopStates());
}

public List<MarketTrendDTO> getTopCountries() {
    return toTrendDTO(
            superstoreRepository.getTopCountries());
}

public List<MarketTrendDTO> getTopCustomerSegments() {
    return toTrendDTO(
            superstoreRepository.getTopCustomerSegments());
}
public List<MarketTrendDTO> getProfitAnalysis() {
    return toTrendDTO(superstoreRepository.getProfitAnalysis());
}

public List<MarketTrendDTO> getDiscountAnalysis() {
    return toTrendDTO(superstoreRepository.getDiscountAnalysis());
}

public List<MarketTrendDTO> getShippingAnalysis() {
    return toTrendDTO(superstoreRepository.getShippingAnalysis());
}

public List<MarketTrendDTO> getBestProducts() {
    return toTrendDTO(superstoreRepository.getBestProducts());
}

public List<MarketTrendDTO> getWorstProducts() {
    return toTrendDTO(superstoreRepository.getWorstProducts());
}
public Page<SearchResultDTO> search(

        String keyword,

        Pageable pageable) {

    return superstoreRepository

            .globalSearch(keyword, pageable)

            .map(this::toSearchDTO);
}
private SearchResultDTO toSearchDTO(
        SuperstoreRecord record) {

    SearchResultDTO dto = new SearchResultDTO();

    dto.setProductName(record.getProductName());

    dto.setCategory(record.getCategory());

    dto.setSubCategory(record.getSubCategory());

    dto.setCustomerName(record.getCustomerName());

    dto.setMarket(record.getMarket());

    dto.setRegion(record.getRegion());

    dto.setState(record.getState());

    dto.setSales(record.getSales());

    dto.setProfit(record.getProfit());

    dto.setQuantity(record.getQuantity());

    dto.setDiscount(record.getDiscount());
    dto.setCity(record.getCity());
dto.setCountry(record.getCountry());

    return dto;
}
public MarketSearchResponseDTO search(

        MarketSearchRequestDTO request,

        Pageable pageable) {

    Page<SuperstoreRecord> page =

            superstoreRepository.findAll(

                    MarketSpecification.search(request),

                    pageable);

    MarketSummaryDTO summary =

            new MarketSummaryDTO();

    summary.setTotalRecords(page.getTotalElements());

    summary.setTotalSales(

            page.getContent()

                    .stream()

                    .map(SuperstoreRecord::getSales)

                    .reduce(BigDecimal.ZERO, BigDecimal::add));

    summary.setTotalProfit(

            page.getContent()

                    .stream()

                    .map(SuperstoreRecord::getProfit)

                    .reduce(BigDecimal.ZERO, BigDecimal::add));

    summary.setTotalQuantity(

            page.getContent()

                    .stream()

                    .mapToLong(SuperstoreRecord::getQuantity)

                    .sum());

    MarketSearchResponseDTO response =

            new MarketSearchResponseDTO();

    response.setSummary(summary);

    response.setResults(

            page.getContent()

                    .stream()

                    .map(this::toSearchDTO)

                    .toList());

    return response;
}
public List<BusinessInsightDTO> getBusinessInsights() {

    List<BusinessInsightDTO> list = new ArrayList<>();

    BusinessInsightDTO revenue = new BusinessInsightDTO();

    revenue.setTitle("Highest Revenue Category");
    revenue.setDescription(

        superstoreRepository

                .highestRevenueCategories()

                .get(0)[0]

                .toString());

    revenue.setType("SUCCESS");

    list.add(revenue);

    BusinessInsightDTO region = new BusinessInsightDTO();

    region.setTitle("Highest Profit Region");

   region.setDescription(

        superstoreRepository

                .highestProfitRegions()

                .get(0)[0]

                .toString());

    region.setType("INFO");

    list.add(region);

    BusinessInsightDTO segment = new BusinessInsightDTO();

    segment.setTitle("Best Customer Segment");

    segment.setDescription(

        superstoreRepository

                .bestCustomerSegments()

                .get(0)[0]

                .toString());

    segment.setType("SUCCESS");

    list.add(segment);

    BusinessInsightDTO loss = new BusinessInsightDTO();

    loss.setTitle("Lowest Profit Product");
    loss.setDescription(

        superstoreRepository

                .lowestProfitProducts()

                .get(0)[0]

                .toString());

    loss.setType("WARNING");

    list.add(loss);

    return list;
}
}
