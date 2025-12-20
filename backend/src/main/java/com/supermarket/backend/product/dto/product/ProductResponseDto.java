package com.supermarket.backend.product.dto.product;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponseDto {

    private Long productId;
    private String productName;
    private String barcode;
    private String categoryName;
    private String brandName;
    private Integer stockQuantity;
    private Integer criticalStockLevel;
    private Double salePrice;
    private Boolean isActive;
}
