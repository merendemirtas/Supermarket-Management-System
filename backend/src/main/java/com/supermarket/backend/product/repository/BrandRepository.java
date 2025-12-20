package com.supermarket.backend.product.repository;

import com.supermarket.backend.product.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    boolean existsByBrandName(String brandName);

    List<Brand> findAllByIsActiveTrue();

    Optional<Brand> findByBrandIdAndIsActiveTrue(Long brandId);
}
