package com.supermarket.backend.dashboard.projection;

public interface TopSellingProductView {

    Long getProductId();
    String getProductName();
    Long getTotalQuantitySold();
}
