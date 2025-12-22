package com.supermarket.backend.operation.repository;

import com.supermarket.backend.operation.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
