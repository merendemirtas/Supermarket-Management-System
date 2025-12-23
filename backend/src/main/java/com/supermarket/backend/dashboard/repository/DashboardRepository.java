package com.supermarket.backend.dashboard.repository;

import com.supermarket.backend.dashboard.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class DashboardRepository {

    private final JdbcTemplate jdbcTemplate;

    public DashboardSummaryDto getDashboardSummary() {
    return jdbcTemplate.query(
            "SELECT * FROM vw_dashboard_summary",
            rs -> {
                if (rs.next()) {
                    return mapSummary(rs);
                }
                return null;
            }
    );
}


    public List<SalesTrendDto> getSalesLast7Days() {
        return jdbcTemplate.query(
                "SELECT * FROM vw_sales_last_7_days",
                (rs, rowNum) -> mapSalesTrend(rs)
        );
    }

    public List<TopSellingProductDto> getTopSellingProducts(int limit) {
        return jdbcTemplate.query(
                "SELECT * FROM vw_top_selling_products LIMIT ?",
                ps -> ps.setInt(1, limit),
                (rs, rowNum) -> mapTopSelling(rs)
        );
    }

    /* ---------- PRIVATE MAPPERS ---------- */

    private DashboardSummaryDto mapSummary(ResultSet rs) throws SQLException {
        return new DashboardSummaryDto(
                rs.getDouble(1), // todaySalesTotal
                rs.getLong(2),   // todaySalesCount
                rs.getLong(3),   // criticalStockProductCount
                rs.getLong(4),   // outOfStockProductCount
                rs.getLong(5),   // totalActiveProductCount
                rs.getLong(6)    // totalSupplierCount
        );
    }

    private SalesTrendDto mapSalesTrend(ResultSet rs) throws SQLException {
    java.sql.Date date = rs.getDate(1);

    return new SalesTrendDto(
            date != null ? date.toLocalDate() : null,
            rs.getDouble(2)
    );
}


    private TopSellingProductDto mapTopSelling(ResultSet rs) throws SQLException {
        return new TopSellingProductDto(
                rs.getLong(1),   // productId
                rs.getString(2), // productName
                rs.getLong(3)    // totalQuantitySold
        );
    }
}
