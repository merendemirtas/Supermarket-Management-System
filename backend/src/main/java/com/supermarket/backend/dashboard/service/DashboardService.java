package com.supermarket.backend.dashboard.service;

import com.supermarket.backend.dashboard.dto.*;
import com.supermarket.backend.dashboard.repository.DashboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DashboardRepository dashboardRepository;

    public DashboardSummaryDto getSummary() {
        return dashboardRepository.getDashboardSummary();
    }

    public List<SalesTrendDto> getSalesLast7Days() {
        return dashboardRepository.getSalesLast7Days();
    }

    public List<TopSellingProductDto> getTopSellingProducts(int limit) {
        return dashboardRepository.getTopSellingProducts(limit);
    }
}
