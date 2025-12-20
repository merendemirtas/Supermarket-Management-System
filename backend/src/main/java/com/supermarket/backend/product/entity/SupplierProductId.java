package com.supermarket.backend.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SupplierProductId implements Serializable {

    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "product_id")
    private Long productId;
}
