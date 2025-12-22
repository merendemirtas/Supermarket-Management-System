package com.supermarket.backend.operation.service;

import com.supermarket.backend.operation.dto.sale.*;
import com.supermarket.backend.operation.entity.Sale;
import com.supermarket.backend.operation.entity.SaleItem;
import com.supermarket.backend.operation.repository.SaleRepository;
import com.supermarket.backend.product.entity.Product;
import com.supermarket.backend.product.repository.ProductRepository;
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
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    @Transactional
    public SaleResponseDto create(SaleCreateRequestDto dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User cashier = userService.findByUsernameOrThrow(username);

        Sale sale = Sale.builder()
                .cashier(cashier)
                .saleDate(LocalDateTime.now())
                .note(dto.getNote())
                .totalAmount(0.0)
                .build();

        double total = 0.0;

        for (SaleItemCreateRequestDto itemDto : dto.getItems()) {
            Product product = productRepository.findByProductIdAndIsActiveTrue(itemDto.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + itemDto.getProductId()));

            int currentStock = product.getStockQuantity() == null ? 0 : product.getStockQuantity();
            if (currentStock < itemDto.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for productId=" + product.getProductId()
                        + " (stock=" + currentStock + ", requested=" + itemDto.getQuantity() + ")");
            }

            // stok düş
            product.setStockQuantity(currentStock - itemDto.getQuantity());
            productRepository.save(product);

            double unitPrice = product.getSalePrice();
            double lineTotal = unitPrice * itemDto.getQuantity();
            total += lineTotal;

            SaleItem saleItem = SaleItem.builder()
                    .sale(sale)
                    .product(product)
                    .quantity(itemDto.getQuantity())
                    .unitPrice(unitPrice)
                    .lineTotal(lineTotal)
                    .build();

            sale.getItems().add(saleItem);
        }

        sale.setTotalAmount(total);

        Sale saved = saleRepository.save(sale);
        return toResponse(saved);
    }

    @SuppressWarnings("null")
    @Transactional(readOnly = true)
    public SaleResponseDto getById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sale not found: " + id));
        return toResponse(sale);
    }

    private SaleResponseDto toResponse(Sale sale) {
        return SaleResponseDto.builder()
                .saleId(sale.getSaleId())
                .cashierUsername(sale.getCashier().getUsername())
                .saleDate(sale.getSaleDate())
                .totalAmount(sale.getTotalAmount())
                .note(sale.getNote())
                .items(
                        sale.getItems().stream()
                                .map(i -> SaleItemResponseDto.builder()
                                        .productId(i.getProduct().getProductId())
                                        .productName(i.getProduct().getProductName())
                                        .quantity(i.getQuantity())
                                        .unitPrice(i.getUnitPrice())
                                        .lineTotal(i.getLineTotal())
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();
    }
}
