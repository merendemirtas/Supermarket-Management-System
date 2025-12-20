package com.supermarket.backend.product.repository;

import com.supermarket.backend.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByCategoryName(String categoryName);

    List<Category> findAllByIsActiveTrue();

    Optional<Category> findByCategoryIdAndIsActiveTrue(Long categoryId);
}
