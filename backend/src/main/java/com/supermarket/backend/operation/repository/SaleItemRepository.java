package com.supermarket.backend.operation.repository;

import com.supermarket.backend.operation.entity.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
}
