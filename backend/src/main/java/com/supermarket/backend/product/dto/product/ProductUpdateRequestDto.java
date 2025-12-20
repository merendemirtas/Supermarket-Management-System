package com.supermarket.backend.product.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateRequestDto {

    @NotBlank
    private String productName;

    @NotNull
    @Min(0)
    private Integer criticalStockLevel;

    @NotNull
    @Min(0)
    private Double salePrice;
}
