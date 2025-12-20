package com.supermarket.backend.product.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryUpdateRequestDto {

    @NotBlank(message = "Category name cannot be blank")
    private String categoryName;
}
