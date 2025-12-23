package com.supermarket.backend.dashboard.projection;

import java.time.LocalDate;

public interface SalesTrendView {

    LocalDate getSaleDate();
    Double getTotalSales();
}
