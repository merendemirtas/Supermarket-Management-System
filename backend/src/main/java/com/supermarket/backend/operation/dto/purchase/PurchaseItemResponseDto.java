package com.supermarket.backend.operation.dto.purchase;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseItemResponseDto {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double unitCost;
    private Double lineTotal;
}
