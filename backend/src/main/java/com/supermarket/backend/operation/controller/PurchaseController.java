package com.supermarket.backend.operation.controller;

import com.supermarket.backend.operation.dto.purchase.PurchaseCreateRequestDto;
import com.supermarket.backend.operation.dto.purchase.PurchaseResponseDto;
import com.supermarket.backend.operation.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PreAuthorize("hasAuthority('STOCK_MANAGE')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseResponseDto create(@Valid @RequestBody PurchaseCreateRequestDto dto) {
        return purchaseService.create(dto);
    }

    @PreAuthorize("hasAuthority('STOCK_MANAGE')")
    @GetMapping("/{id}")
    public PurchaseResponseDto getById(@PathVariable Long id) {
        return purchaseService.getById(id);
    }
}
