package com.supermarket.backend.dashboard.controller;

import com.supermarket.backend.dashboard.dto.*;
import com.supermarket.backend.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @PreAuthorize("hasAuthority('REPORT_VIEW')")
    @GetMapping("/summary")
    public DashboardSummaryDto getSummary() {
        return dashboardService.getSummary();
    }

    @PreAuthorize("hasAuthority('REPORT_VIEW')")
    @GetMapping("/sales-last-7-days")
    public List<SalesTrendDto> getSalesLast7Days() {
        return dashboardService.getSalesLast7Days();
    }

    @PreAuthorize("hasAuthority('REPORT_VIEW')")
    @GetMapping("/top-selling-products")
    public List<TopSellingProductDto> getTopSellingProducts(
            @RequestParam(defaultValue = "5") int limit
    ) {
        return dashboardService.getTopSellingProducts(limit);
    }
}
