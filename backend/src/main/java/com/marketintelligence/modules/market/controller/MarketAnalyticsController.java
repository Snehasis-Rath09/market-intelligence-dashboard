package com.marketintelligence.modules.market.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marketintelligence.common.response.ApiResponse;
import com.marketintelligence.modules.market.dto.BusinessInsightDTO;
import com.marketintelligence.modules.market.dto.CategoryPerformanceDTO;
import com.marketintelligence.modules.market.dto.GeographyDemandDTO;
import com.marketintelligence.modules.market.dto.MarketSearchRequestDTO;
import com.marketintelligence.modules.market.dto.MarketSearchResponseDTO;
import com.marketintelligence.modules.market.dto.MarketTrendDTO;
import com.marketintelligence.modules.market.dto.RegionalDemandDTO;
import com.marketintelligence.modules.market.service.MarketAnalyticsService;

/**
 * Market Intelligence endpoints — all data here is sourced from the
 * Global Superstore public dataset (read-only, seeded once via Flyway).
 * Not tenant-scoped, since this is shared reference data, not live business data.
 */
@RestController
@RequestMapping("/api/market")
public class MarketAnalyticsController {

    private final MarketAnalyticsService marketAnalyticsService;

    public MarketAnalyticsController(MarketAnalyticsService marketAnalyticsService) {
        this.marketAnalyticsService = marketAnalyticsService;
    }

    @GetMapping("/category-performance")
    public ApiResponse<List<CategoryPerformanceDTO>> getCategoryPerformance() {
        return ApiResponse.success(marketAnalyticsService.getCategoryPerformance());
    }

    @GetMapping("/regional-demand")
    public ApiResponse<List<RegionalDemandDTO>> getRegionalDemand() {
        return ApiResponse.success(marketAnalyticsService.getRegionalDemand());
    }

    @GetMapping("/monthly-trend")
    public ApiResponse<List<MarketTrendDTO>> getMonthlyTrend() {
        return ApiResponse.success(marketAnalyticsService.getMonthlyTrend());
    }

    @PostMapping("/geography-demand")
    public ApiResponse<List<GeographyDemandDTO>> getGeographyDemand(@RequestBody MarketSearchRequestDTO request) {
        return ApiResponse.success(marketAnalyticsService.getGeographyDemand(request));
    }
   @PostMapping("/search")
public ApiResponse<MarketSearchResponseDTO> search(

        @RequestBody MarketSearchRequestDTO request,

        @RequestParam(defaultValue = "0") int page,

        @RequestParam(defaultValue = "20") int size,

        @RequestParam(defaultValue = "sales") String sortBy,

        @RequestParam(defaultValue = "desc") String direction) {

    Pageable pageable = PageRequest.of(

            page,

            size,

            Sort.by(

                    Sort.Direction.fromString(direction),

                    sortBy));

    return ApiResponse.success(

            marketAnalyticsService.search(

                    request,

                    pageable));
}
@GetMapping("/top-products")
public ApiResponse<List<MarketTrendDTO>> getTopProducts() {
    return ApiResponse.success(
            marketAnalyticsService.getTopProducts());
}

@GetMapping("/top-subcategories")
public ApiResponse<List<MarketTrendDTO>> getTopSubCategories() {
    return ApiResponse.success(
            marketAnalyticsService.getTopSubCategories());
}

@GetMapping("/top-cities")
public ApiResponse<List<MarketTrendDTO>> getTopCities() {
    return ApiResponse.success(
            marketAnalyticsService.getTopCities());
}

@GetMapping("/top-states")
public ApiResponse<List<MarketTrendDTO>> getTopStates() {
    return ApiResponse.success(
            marketAnalyticsService.getTopStates());
}

@GetMapping("/top-countries")
public ApiResponse<List<MarketTrendDTO>> getTopCountries() {
    return ApiResponse.success(
            marketAnalyticsService.getTopCountries());
}

@GetMapping("/top-segments")
public ApiResponse<List<MarketTrendDTO>> getTopCustomerSegments() {
    return ApiResponse.success(
            marketAnalyticsService.getTopCustomerSegments());
}
@GetMapping("/profit-analysis")
public ApiResponse<List<MarketTrendDTO>> getProfitAnalysis() {
    return ApiResponse.success(
            marketAnalyticsService.getProfitAnalysis());
}

@GetMapping("/discount-analysis")
public ApiResponse<List<MarketTrendDTO>> getDiscountAnalysis() {
    return ApiResponse.success(
            marketAnalyticsService.getDiscountAnalysis());
}

@GetMapping("/shipping-analysis")
public ApiResponse<List<MarketTrendDTO>> getShippingAnalysis() {
    return ApiResponse.success(
            marketAnalyticsService.getShippingAnalysis());
}

@GetMapping("/best-products")
public ApiResponse<List<MarketTrendDTO>> getBestProducts() {
    return ApiResponse.success(
            marketAnalyticsService.getBestProducts());
}

@GetMapping("/worst-products")
public ApiResponse<List<MarketTrendDTO>> getWorstProducts() {
    return ApiResponse.success(
            marketAnalyticsService.getWorstProducts());
}
@GetMapping("/insights")
public ApiResponse<List<BusinessInsightDTO>> getBusinessInsights() {

    return ApiResponse.success(

            marketAnalyticsService.getBusinessInsights());

}
}
