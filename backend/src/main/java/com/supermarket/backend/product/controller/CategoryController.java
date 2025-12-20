package com.supermarket.backend.product.controller;

import com.supermarket.backend.product.dto.category.CategoryCreateRequestDto;
import com.supermarket.backend.product.dto.category.CategoryUpdateRequestDto;
import com.supermarket.backend.product.dto.category.CategoryResponseDto;
import com.supermarket.backend.product.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasAuthority('CATEGORY_MANAGE')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto create(
            @Valid @RequestBody CategoryCreateRequestDto dto
    ) {
        return categoryService.create(dto);
    }

    @PreAuthorize("hasAuthority('CATEGORY_VIEW')")
    @GetMapping
    public List<CategoryResponseDto> getAllActive() {
        return categoryService.getAllActive();
    }

    @PreAuthorize("hasAuthority('CATEGORY_MANAGE')")
    @PutMapping("/{id}")
    public CategoryResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryUpdateRequestDto dto
    ) {
        return categoryService.update(id, dto);
    }

    @PreAuthorize("hasAuthority('CATEGORY_MANAGE')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable Long id) {
        categoryService.deactivate(id);
    }
}
