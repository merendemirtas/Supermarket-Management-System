package com.supermarket.backend.dashboard.projection;

public interface DashboardSummaryView {

    Double getTodaySalesTotal();
    Long getTodaySalesCount();

    Long getCriticalStockProductCount();
    Long getOutOfStockProductCount();

    Long getTotalActiveProductCount();
    Long getTotalSupplierCount();
}
