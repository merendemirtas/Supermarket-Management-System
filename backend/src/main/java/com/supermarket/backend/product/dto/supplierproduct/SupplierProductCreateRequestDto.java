package com.supermarket.backend.product.dto.supplierproduct;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierProductCreateRequestDto {

    @NotNull
    private Long supplierId;

    @NotNull
    private Long productId;

    @NotNull
    @Min(0)
    private Double purchasePrice;
}
