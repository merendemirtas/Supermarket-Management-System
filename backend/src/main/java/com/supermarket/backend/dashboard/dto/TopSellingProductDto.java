package com.supermarket.backend.dashboard.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopSellingProductDto {

    private Long productId;
    private String productName;
    private Long totalQuantitySold;
}
