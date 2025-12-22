package com.supermarket.backend.product.controller;

import com.supermarket.backend.product.dto.product.ProductCreateRequestDto;
import com.supermarket.backend.product.dto.product.ProductResponseDto;
import com.supermarket.backend.product.dto.product.ProductUpdateRequestDto;
import com.supermarket.backend.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasAuthority('PRODUCT_MANAGE')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto create(@Valid @RequestBody ProductCreateRequestDto dto) {
        return productService.create(dto);
    }

    @PreAuthorize("hasAuthority('PRODUCT_VIEW')")
    @GetMapping
    public List<ProductResponseDto> getAllActive() {
        return productService.getAllActive();
    }

    @PreAuthorize("hasAuthority('PRODUCT_VIEW')")
    @GetMapping("/critical-stock")
    public List<ProductResponseDto> getCriticalStockProducts() {
        return productService.getCriticalStockProducts();
    }

    @PreAuthorize("hasAuthority('PRODUCT_MANAGE')")
    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequestDto dto) {
        return productService.update(id, dto);
    }

    @PreAuthorize("hasAuthority('PRODUCT_MANAGE')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable Long id) {
        productService.deactivate(id);
    }
}
