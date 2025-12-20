package com.supermarket.backend.product.repository;

import com.supermarket.backend.product.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    boolean existsBySupplierName(String supplierName);

    List<Supplier> findAllByIsActiveTrue();

    Optional<Supplier> findBySupplierIdAndIsActiveTrue(Long supplierId);
}
