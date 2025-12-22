package com.supermarket.backend.operation.service;

import com.supermarket.backend.operation.dto.purchase.*;
import com.supermarket.backend.operation.entity.Purchase;
import com.supermarket.backend.operation.entity.PurchaseItem;
import com.supermarket.backend.operation.repository.PurchaseRepository;
import com.supermarket.backend.product.entity.Product;
import com.supermarket.backend.product.entity.Supplier;
import com.supermarket.backend.product.repository.ProductRepository;
import com.supermarket.backend.product.repository.SupplierProductRepository;
import com.supermarket.backend.product.repository.SupplierRepository;
import com.supermarket.backend.user.entity.User;
import com.supermarket.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final SupplierProductRepository supplierProductRepository;

    private final UserService userService;

    @SuppressWarnings("null")
    @Transactional
    public PurchaseResponseDto create(PurchaseCreateRequestDto dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User createdBy = userService.findByUsernameOrThrow(username);

        Supplier supplier = supplierRepository.findBySupplierIdAndIsActiveTrue(dto.getSupplierId())
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found: " + dto.getSupplierId()));

        Purchase purchase = Purchase.builder()
                .supplier(supplier)
                .createdBy(createdBy)
                .purchaseDate(LocalDateTime.now())
                .note(dto.getNote())
                .build();

        for (PurchaseItemCreateRequestDto itemDto : dto.getItems()) {
            Product product = productRepository.findByProductIdAndIsActiveTrue(itemDto.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + itemDto.getProductId()));

            // tedarikçi-ürün ilişki kontrolü (SupplierProduct)
            boolean linked = supplierProductRepository.existsBySupplier_SupplierIdAndProduct_ProductId(
                    supplier.getSupplierId(), product.getProductId()
            );
            if (!linked) {
                throw new IllegalArgumentException("Supplier " + supplier.getSupplierId()
                        + " is not linked to product " + product.getProductId());
            }

            // stok artır
            int currentStock = product.getStockQuantity() == null ? 0 : product.getStockQuantity();
            product.setStockQuantity(currentStock + itemDto.getQuantity());
            productRepository.save(product);

            Double unitCost = itemDto.getUnitCost();
            Double lineTotal = (unitCost == null) ? null : unitCost * itemDto.getQuantity();

            PurchaseItem purchaseItem = PurchaseItem.builder()
                    .purchase(purchase)
                    .product(product)
                    .quantity(itemDto.getQuantity())
                    .unitCost(unitCost)
                    .lineTotal(lineTotal)
                    .build();

            purchase.getItems().add(purchaseItem);
        }

        Purchase saved = purchaseRepository.save(purchase);
        return toResponse(saved);
    }

    @SuppressWarnings("null")
    @Transactional(readOnly = true)
    public PurchaseResponseDto getById(Long id) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Purchase not found: " + id));
        return toResponse(purchase);
    }

    private PurchaseResponseDto toResponse(Purchase purchase) {
        return PurchaseResponseDto.builder()
                .purchaseId(purchase.getPurchaseId())
                .supplierId(purchase.getSupplier().getSupplierId())
                .supplierName(purchase.getSupplier().getSupplierName())
                .createdByUsername(purchase.getCreatedBy().getUsername())
                .purchaseDate(purchase.getPurchaseDate())
                .note(purchase.getNote())
                .items(
                        purchase.getItems().stream()
                                .map(i -> PurchaseItemResponseDto.builder()
                                        .productId(i.getProduct().getProductId())
                                        .productName(i.getProduct().getProductName())
                                        .quantity(i.getQuantity())
                                        .unitCost(i.getUnitCost())
                                        .lineTotal(i.getLineTotal())
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();
    }
}
