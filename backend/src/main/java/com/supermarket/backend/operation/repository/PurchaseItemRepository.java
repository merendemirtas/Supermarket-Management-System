package com.supermarket.backend.operation.repository;

import com.supermarket.backend.operation.entity.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {
}
