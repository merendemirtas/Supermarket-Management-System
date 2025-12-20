package com.supermarket.backend.product.repository;

import com.supermarket.backend.product.entity.SupplierProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierProductRepository extends JpaRepository<SupplierProduct, Long> {

    boolean existsBySupplier_SupplierIdAndProduct_ProductId(
            Long supplierId,
            Long productId
    );

    Optional<SupplierProduct> findBySupplierProductIdAndIsActiveTrue(Long id);

    List<SupplierProduct> findAllByProduct_ProductIdAndIsActiveTrue(Long productId);
}
