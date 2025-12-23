package com.supermarket.backend.dashboard.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesTrendDto {

    private LocalDate saleDate;
    private Double totalSales;
}
