package com.supermarket.backend.operation.repository;

import com.supermarket.backend.operation.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
