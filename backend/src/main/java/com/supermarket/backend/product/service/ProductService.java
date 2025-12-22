package com.supermarket.backend.product.service;

import com.supermarket.backend.product.dto.product.ProductCreateRequestDto;
import com.supermarket.backend.product.dto.product.ProductResponseDto;
import com.supermarket.backend.product.dto.product.ProductUpdateRequestDto;
import com.supermarket.backend.product.entity.Brand;
import com.supermarket.backend.product.entity.Category;
import com.supermarket.backend.product.entity.Product;
import com.supermarket.backend.product.repository.BrandRepository;
import com.supermarket.backend.product.repository.CategoryRepository;
import com.supermarket.backend.product.repository.ProductRepository;
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

    /**
     * FIX: Projede kritik stok mantığı "stockQuantity < criticalStockLevel" olmalı.
     * Eski kod 0 ile kıyaslıyordu; bu da pratikte yanlış sonuç üretir.
     */
    public List<ProductResponseDto> getCriticalStockProducts() {
        return productRepository.findAllByIsActiveTrue()
                .stream()
                .filter(p -> p.getStockQuantity() != null
                        && p.getCriticalStockLevel() != null
                        && p.getStockQuantity() < p.getCriticalStockLevel())
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
