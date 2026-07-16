package com.marketintelligence.modules.market.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.marketintelligence.modules.market.dto.MarketSearchRequestDTO;
import com.marketintelligence.modules.market.entity.SuperstoreRecord;

import jakarta.persistence.criteria.Predicate;

public class MarketSpecification {

    public static Specification<SuperstoreRecord> search(
            MarketSearchRequestDTO request) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

           if (request.getKeyword() != null && !request.getKeyword().isBlank()) {

    String keyword = "%" + request.getKeyword().toLowerCase().trim() + "%";

    predicates.add(cb.or(

            cb.like(cb.lower(root.get("productName")), keyword),

            cb.like(cb.lower(root.get("category")), keyword),

            cb.like(cb.lower(root.get("subCategory")), keyword),

            cb.like(cb.lower(root.get("customerName")), keyword),

            cb.like(cb.lower(root.get("market")), keyword),

            cb.like(cb.lower(root.get("region")), keyword),

            cb.like(cb.lower(root.get("country")), keyword),

            cb.like(cb.lower(root.get("state")), keyword),

            cb.like(cb.lower(root.get("city")), keyword),

            cb.like(cb.lower(root.get("customerSegment")), keyword),

            cb.like(cb.lower(root.get("shipMode")), keyword),

            cb.like(cb.lower(root.get("orderPriority")), keyword),

            cb.like(cb.lower(root.get("orderId")), keyword)

    ));
}

            if (request.getCategory() != null)
                predicates.add(cb.equal(root.get("category"), request.getCategory()));

            if (request.getSubCategory() != null)
                predicates.add(cb.equal(root.get("subCategory"), request.getSubCategory()));

            if (request.getMarket() != null)
                predicates.add(cb.equal(root.get("market"), request.getMarket()));

            if (request.getRegion() != null)
                predicates.add(cb.equal(root.get("region"), request.getRegion()));

            if (request.getMinSales() != null)
                predicates.add(cb.greaterThanOrEqualTo(root.get("sales"), request.getMinSales()));

            if (request.getMaxSales() != null)
                predicates.add(cb.lessThanOrEqualTo(root.get("sales"), request.getMaxSales()));

            if (request.getMinProfit() != null)
                predicates.add(cb.greaterThanOrEqualTo(root.get("profit"), request.getMinProfit()));

            if (request.getMaxProfit() != null)
                predicates.add(cb.lessThanOrEqualTo(root.get("profit"), request.getMaxProfit()));

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}