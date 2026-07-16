package com.marketintelligence.modules.market.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.marketintelligence.modules.market.entity.SuperstoreRecord;

public interface SuperstoreRepository
        extends JpaRepository<SuperstoreRecord, Long>,
                JpaSpecificationExecutor<SuperstoreRecord> {

    @Query("SELECT s.category AS category, SUM(s.sales) AS totalSales, " +
           "SUM(s.profit) AS totalProfit, SUM(s.quantity) AS totalUnits " +
           "FROM SuperstoreRecord s GROUP BY s.category ORDER BY totalSales DESC")
    List<CategoryAggregate> aggregateByCategory();

    @Query("SELECT s.region AS region, SUM(s.sales) AS totalSales " +
           "FROM SuperstoreRecord s GROUP BY s.region ORDER BY totalSales DESC")
    List<RegionAggregate> aggregateByRegion();

    @Query("SELECT YEAR(s.orderDate), MONTH(s.orderDate), SUM(s.sales) " +
           "FROM SuperstoreRecord s GROUP BY YEAR(s.orderDate), MONTH(s.orderDate) " +
           "ORDER BY YEAR(s.orderDate), MONTH(s.orderDate)")
    List<Object[]> aggregateByMonth();

    interface CategoryAggregate {
        String getCategory();
        java.math.BigDecimal getTotalSales();
        java.math.BigDecimal getTotalProfit();
        Long getTotalUnits();
    }

    interface RegionAggregate {
        String getRegion();
        java.math.BigDecimal getTotalSales();
    }

@Query("""
SELECT s
FROM SuperstoreRecord s
WHERE

LOWER(s.productName) LIKE LOWER(CONCAT('%',:keyword,'%'))

OR LOWER(s.category) LIKE LOWER(CONCAT('%',:keyword,'%'))

OR LOWER(s.subCategory) LIKE LOWER(CONCAT('%',:keyword,'%'))

OR LOWER(s.customerName) LIKE LOWER(CONCAT('%',:keyword,'%'))

OR LOWER(s.region) LIKE LOWER(CONCAT('%',:keyword,'%'))

OR LOWER(s.state) LIKE LOWER(CONCAT('%',:keyword,'%'))

OR LOWER(s.country) LIKE LOWER(CONCAT('%',:keyword,'%'))

OR LOWER(s.market) LIKE LOWER(CONCAT('%',:keyword,'%'))
""")
Page<SuperstoreRecord> globalSearch(

        @Param("keyword") String keyword,

        Pageable pageable);
@Query("""
SELECT s.productName,
SUM(s.sales)
FROM SuperstoreRecord s
GROUP BY s.productName
ORDER BY SUM(s.sales) DESC
""")
List<Object[]> getTopProducts();
@Query("""
SELECT s.subCategory,
SUM(s.sales)
FROM SuperstoreRecord s
GROUP BY s.subCategory
ORDER BY SUM(s.sales) DESC
""")
List<Object[]> getTopSubCategories();
@Query("""
SELECT s.city,
SUM(s.sales)
FROM SuperstoreRecord s
GROUP BY s.city
ORDER BY SUM(s.sales) DESC
""")
List<Object[]> getTopCities();
@Query("""
SELECT s.state,
SUM(s.sales)
FROM SuperstoreRecord s
GROUP BY s.state
ORDER BY SUM(s.sales) DESC
""")
List<Object[]> getTopStates();
@Query("""
SELECT s.country,
SUM(s.sales)
FROM SuperstoreRecord s
GROUP BY s.country
ORDER BY SUM(s.sales) DESC
""")
List<Object[]> getTopCountries();
@Query("""
SELECT s.customerSegment,
SUM(s.sales)
FROM SuperstoreRecord s
GROUP BY s.customerSegment
ORDER BY SUM(s.sales) DESC
""")
List<Object[]> getTopCustomerSegments();
@Query("""
SELECT s.category,
SUM(s.profit)
FROM SuperstoreRecord s
GROUP BY s.category
ORDER BY SUM(s.profit) DESC
""")
List<Object[]> getProfitAnalysis();
@Query("""
SELECT s.category,
AVG(s.discount)
FROM SuperstoreRecord s
GROUP BY s.category
ORDER BY AVG(s.discount) DESC
""")
List<Object[]> getDiscountAnalysis();
@Query("""
SELECT s.shipMode,
AVG(s.shippingCost)
FROM SuperstoreRecord s
GROUP BY s.shipMode
ORDER BY AVG(s.shippingCost) DESC
""")
List<Object[]> getShippingAnalysis();
@Query("""
SELECT s.productName,
SUM(s.profit)
FROM SuperstoreRecord s
GROUP BY s.productName
ORDER BY SUM(s.profit) DESC
""")
List<Object[]> getBestProducts();
@Query("""
SELECT s.productName,
SUM(s.profit)
FROM SuperstoreRecord s
GROUP BY s.productName
ORDER BY SUM(s.profit) ASC
""")
List<Object[]> getWorstProducts();
@Query("""
SELECT s.category,
SUM(s.sales)
FROM SuperstoreRecord s
GROUP BY s.category
ORDER BY SUM(s.sales) DESC
""")
List<Object[]> highestRevenueCategories();
@Query("""
SELECT s.region,
SUM(s.profit)
FROM SuperstoreRecord s
GROUP BY s.region
ORDER BY SUM(s.profit) DESC
""")
List<Object[]> highestProfitRegions();
@Query("""
SELECT s.customerSegment,
SUM(s.sales)
FROM SuperstoreRecord s
GROUP BY s.customerSegment
ORDER BY SUM(s.sales) DESC
""")
List<Object[]> bestCustomerSegments();
@Query("""
SELECT s.productName,
SUM(s.profit)
FROM SuperstoreRecord s
GROUP BY s.productName
ORDER BY SUM(s.profit) ASC
""")
List<Object[]> lowestProfitProducts();
}
