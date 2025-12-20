package com.supermarket.backend.product.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "supplier_product",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"supplier_id", "product_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_product_id")
    private Long supplierProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "purchase_price", nullable = false)
    private Double purchasePrice;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}
