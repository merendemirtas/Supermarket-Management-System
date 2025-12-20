package com.supermarket.backend.product.dto.supplier;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SupplierResponseDto {

    private Long supplierId;
    private String supplierName;
    private String contactName;
    private String phone;
    private String email;
    private String address;
    private Boolean isActive;
}
