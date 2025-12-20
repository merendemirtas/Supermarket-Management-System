package com.supermarket.backend.product.service;

import com.supermarket.backend.product.dto.category.CategoryCreateRequestDto;
import com.supermarket.backend.product.dto.category.CategoryUpdateRequestDto;
import com.supermarket.backend.product.dto.category.CategoryResponseDto;
import com.supermarket.backend.product.entity.Category;
import com.supermarket.backend.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponseDto create(CategoryCreateRequestDto dto) {
        if (categoryRepository.existsByCategoryName(dto.getCategoryName())) {
            throw new IllegalArgumentException("Category already exists");
        }

        Category category = new Category();
        category.setCategoryName(dto.getCategoryName());
        category.setIsActive(true);

        Category savedCategory = categoryRepository.save(category);
        return toResponse(savedCategory);
    }

    public List<CategoryResponseDto> getAllActive() {
        return categoryRepository.findAllByIsActiveTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CategoryResponseDto update(Long id, CategoryUpdateRequestDto dto) {
        Category category = categoryRepository.findByCategoryIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        category.setCategoryName(dto.getCategoryName());
        categoryRepository.save(category);

        return toResponse(category);
    }

    public void deactivate(Long id) {
        Category category = categoryRepository.findByCategoryIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        category.setIsActive(false);
        categoryRepository.save(category);
    }

    private CategoryResponseDto toResponse(Category category) {
        return CategoryResponseDto.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .isActive(category.getIsActive())
                .build();
    }
}
