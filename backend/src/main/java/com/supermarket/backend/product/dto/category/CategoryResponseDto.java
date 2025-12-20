package com.supermarket.backend.product.dto.category;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponseDto {

    private Long categoryId;
    private String categoryName;
    private Boolean isActive;
}
