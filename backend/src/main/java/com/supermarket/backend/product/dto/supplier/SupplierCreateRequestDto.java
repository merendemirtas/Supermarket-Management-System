package com.supermarket.backend.product.dto.supplier;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierCreateRequestDto {

    @NotBlank(message = "Supplier name cannot be blank")
    private String supplierName;

    private String contactName;
    private String phone;
    private String email;
    private String address;
}
