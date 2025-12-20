package com.supermarket.backend.product.dto.brand;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandUpdateRequestDto {

    @NotBlank(message = "Brand name cannot be blank")
    private String brandName;
}
