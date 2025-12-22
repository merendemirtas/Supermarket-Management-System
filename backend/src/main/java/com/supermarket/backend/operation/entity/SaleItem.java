package com.supermarket.backend.operation.entity;

import com.supermarket.backend.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sale_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_item_id")
    private Long saleItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    // satış anındaki fiyat snapshot (çok doğru tasarım)
    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(name = "line_total", nullable = false)
    private Double lineTotal;
}

