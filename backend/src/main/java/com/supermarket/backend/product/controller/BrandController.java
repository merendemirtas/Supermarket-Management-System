package com.supermarket.backend.product.controller;

import com.supermarket.backend.product.dto.brand.BrandCreateRequestDto;
import com.supermarket.backend.product.dto.brand.BrandResponseDto;
import com.supermarket.backend.product.dto.brand.BrandUpdateRequestDto;
import com.supermarket.backend.product.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PreAuthorize("hasAuthority('BRAND_MANAGE')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BrandResponseDto create(@Valid @RequestBody BrandCreateRequestDto dto) {
        return brandService.create(dto);
    }

    @PreAuthorize("hasAuthority('BRAND_VIEW')")
    @GetMapping
    public List<BrandResponseDto> getAllActive() {
        return brandService.getAllActive();
    }

    @PreAuthorize("hasAuthority('BRAND_MANAGE')")
    @PutMapping("/{id}")
    public BrandResponseDto update(@PathVariable Long id, @Valid @RequestBody BrandUpdateRequestDto dto) {
        return brandService.update(id, dto);
    }

    @PreAuthorize("hasAuthority('BRAND_MANAGE')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable Long id) {
        brandService.deactivate(id);
    }
}
