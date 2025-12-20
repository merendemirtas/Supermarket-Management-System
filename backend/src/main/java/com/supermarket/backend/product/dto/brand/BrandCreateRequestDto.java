package com.supermarket.backend.product.dto.brand;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandCreateRequestDto {

    @NotBlank(message = "Brand name cannot be blank")
    private String brandName;
}

