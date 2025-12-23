package com.supermarket.backend.dashboard.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardSummaryDto {

    private Double todaySalesTotal;
    private Long todaySalesCount;
    private Long criticalStockProductCount;
    private Long outOfStockProductCount;
    private Long totalActiveProductCount;
    private Long totalSupplierCount;
}
