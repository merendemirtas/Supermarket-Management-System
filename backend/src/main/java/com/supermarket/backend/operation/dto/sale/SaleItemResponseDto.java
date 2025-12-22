package com.supermarket.backend.operation.dto.sale;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleItemResponseDto {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double unitPrice;
    private Double lineTotal;
}
