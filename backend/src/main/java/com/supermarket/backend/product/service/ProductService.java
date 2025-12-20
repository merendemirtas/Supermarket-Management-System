package com.supermarket.backend.product.service;

import com.supermarket.backend.product.dto.product.*;
import com.supermarket.backend.product.entity.*;
import com.supermarket.backend.product.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    public ProductResponseDto create(ProductCreateRequestDto dto) {

        if (productRepository.existsByBarcode(dto.getBarcode())) {
            throw new IllegalArgumentException("Barcode already exists");
        }

        Category category = categoryRepository
                .findByCategoryIdAndIsActiveTrue(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Brand brand = brandRepository
                .findByBrandIdAndIsActiveTrue(dto.getBrandId())
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));

        Product product = new Product();
        product.setProductName(dto.getProductName());
        product.setBarcode(dto.getBarcode());
        product.setCategory(category);
        product.setBrand(brand);
        product.setStockQuantity(dto.getStockQuantity());
        product.setCriticalStockLevel(dto.getCriticalStockLevel());
        product.setSalePrice(dto.getSalePrice());
        product.setIsActive(true);

        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    public List<ProductResponseDto> getAllActive() {
        return productRepository.findAllByIsActiveTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ProductResponseDto> getCriticalStockProducts() {
        return productRepository.findAllByStockQuantityLessThanAndIsActiveTrue(0)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductResponseDto update(Long id, ProductUpdateRequestDto dto) {
        Product product = productRepository
                .findByProductIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        product.setProductName(dto.getProductName());
        product.setCriticalStockLevel(dto.getCriticalStockLevel());
        product.setSalePrice(dto.getSalePrice());

        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    public void deactivate(Long id) {
        Product product = productRepository
                .findByProductIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        product.setIsActive(false);
        productRepository.save(product);
    }

    private ProductResponseDto toResponse(Product product) {
        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .barcode(product.getBarcode())
                .categoryName(product.getCategory().getCategoryName())
                .brandName(product.getBrand().getBrandName())
                .stockQuantity(product.getStockQuantity())
                .criticalStockLevel(product.getCriticalStockLevel())
                .salePrice(product.getSalePrice())
                .isActive(product.getIsActive())
                .build();
    }
}
