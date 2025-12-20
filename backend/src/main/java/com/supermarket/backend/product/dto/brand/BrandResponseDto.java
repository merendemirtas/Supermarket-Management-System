package com.supermarket.backend.product.dto.brand;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BrandResponseDto {

    private Long brandId;
    private String brandName;
    private Boolean isActive;
}
