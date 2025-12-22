package com.supermarket.backend.product.service;

import com.supermarket.backend.product.dto.brand.BrandCreateRequestDto;
import com.supermarket.backend.product.dto.brand.BrandResponseDto;
import com.supermarket.backend.product.dto.brand.BrandUpdateRequestDto;
import com.supermarket.backend.product.entity.Brand;
import com.supermarket.backend.product.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandResponseDto create(BrandCreateRequestDto dto) {
        if (brandRepository.existsByBrandName(dto.getBrandName())) {
            throw new IllegalArgumentException("Brand already exists");
        }

        Brand brand = new Brand();
        brand.setBrandName(dto.getBrandName());
        brand.setIsActive(true);

        Brand saved = brandRepository.save(brand);
        return toResponse(saved);
    }

    public List<BrandResponseDto> getAllActive() {
        return brandRepository.findAllByIsActiveTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public BrandResponseDto update(Long id, BrandUpdateRequestDto dto) {
        Brand brand = brandRepository.findByBrandIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));

        brand.setBrandName(dto.getBrandName());
        brandRepository.save(brand);

        return toResponse(brand);
    }

    public void deactivate(Long id) {
        Brand brand = brandRepository.findByBrandIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));

        brand.setIsActive(false);
        brandRepository.save(brand);
    }

    private BrandResponseDto toResponse(Brand brand) {
        return BrandResponseDto.builder()
                .brandId(brand.getBrandId())
                .brandName(brand.getBrandName())
                .isActive(brand.getIsActive())
                .build();
    }
}
