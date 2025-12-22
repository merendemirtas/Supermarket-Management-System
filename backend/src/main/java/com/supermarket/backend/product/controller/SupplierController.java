package com.supermarket.backend.product.controller;

import com.supermarket.backend.product.dto.supplier.SupplierCreateRequestDto;
import com.supermarket.backend.product.dto.supplier.SupplierResponseDto;
import com.supermarket.backend.product.dto.supplier.SupplierUpdateRequestDto;
import com.supermarket.backend.product.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PreAuthorize("hasAuthority('SUPPLIER_MANAGE')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SupplierResponseDto create(@Valid @RequestBody SupplierCreateRequestDto dto) {
        return supplierService.create(dto);
    }

    @PreAuthorize("hasAuthority('SUPPLIER_VIEW')")
    @GetMapping
    public List<SupplierResponseDto> getAllActive() {
        return supplierService.getAllActive();
    }

    @PreAuthorize("hasAuthority('SUPPLIER_MANAGE')")
    @PutMapping("/{id}")
    public SupplierResponseDto update(@PathVariable Long id, @Valid @RequestBody SupplierUpdateRequestDto dto) {
        return supplierService.update(id, dto);
    }

    @PreAuthorize("hasAuthority('SUPPLIER_MANAGE')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable Long id) {
        supplierService.deactivate(id);
    }
}
