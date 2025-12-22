package com.supermarket.backend.operation.controller;

import com.supermarket.backend.operation.dto.sale.SaleCreateRequestDto;
import com.supermarket.backend.operation.dto.sale.SaleResponseDto;
import com.supermarket.backend.operation.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PreAuthorize("hasAuthority('SALE_CREATE')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaleResponseDto create(@Valid @RequestBody SaleCreateRequestDto dto) {
        return saleService.create(dto);
    }

    @PreAuthorize("hasAuthority('SALE_VIEW')")
    @GetMapping("/{id}")
    public SaleResponseDto getById(@PathVariable Long id) {
        return saleService.getById(id);
    }
}
