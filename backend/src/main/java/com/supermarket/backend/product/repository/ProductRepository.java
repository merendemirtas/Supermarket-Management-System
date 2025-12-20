package com.supermarket.backend.product.repository;

import com.supermarket.backend.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByBarcode(String barcode);

    Optional<Product> findByProductIdAndIsActiveTrue(Long productId);

    List<Product> findAllByIsActiveTrue();

    List<Product> findAllByStockQuantityLessThanAndIsActiveTrue(
            Integer criticalStockLevel
    );
}
