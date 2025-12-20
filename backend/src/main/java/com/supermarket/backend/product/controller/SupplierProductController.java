package com.supermarket.backend.product.controller;

import com.supermarket.backend.product.dto.supplierproduct.SupplierProductCreateRequestDto;
import com.supermarket.backend.product.dto.supplierproduct.SupplierProductResponseDto;
import com.supermarket.backend.product.service.SupplierProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier-products")
@RequiredArgsConstructor
public class SupplierProductController {

    private final SupplierProductService supplierProductService;

    @PreAuthorize("hasAuthority('SUPPLIER_PRODUCT_MANAGE')")
    @PostMapping
    public SupplierProductResponseDto create(
            @Valid @RequestBody SupplierProductCreateRequestDto dto
    ) {
        return supplierProductService.create(dto);
    }

    @PreAuthorize("hasAuthority('SUPPLIER_PRODUCT_VIEW')")
    @GetMapping("/product/{productId}")
    public List<SupplierProductResponseDto> getByProduct(
            @PathVariable Long productId
    ) {
        return supplierProductService.getByProduct(productId);
    }
}
