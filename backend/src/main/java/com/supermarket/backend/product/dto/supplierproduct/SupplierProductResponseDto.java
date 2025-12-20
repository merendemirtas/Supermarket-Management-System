package com.supermarket.backend.product.dto.supplierproduct;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SupplierProductResponseDto {

    private Long supplierProductId;

    private Long supplierId;
    private String supplierName;

    private Long productId;
    private String productName;

    private Double purchasePrice;
    private Boolean isActive;
}
