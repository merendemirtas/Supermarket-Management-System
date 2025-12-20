package com.supermarket.backend.product.service;

import com.supermarket.backend.product.dto.supplierproduct.SupplierProductCreateRequestDto;
import com.supermarket.backend.product.dto.supplierproduct.SupplierProductResponseDto;
import com.supermarket.backend.product.entity.Product;
import com.supermarket.backend.product.entity.Supplier;
import com.supermarket.backend.product.entity.SupplierProduct;
import com.supermarket.backend.product.repository.ProductRepository;
import com.supermarket.backend.product.repository.SupplierProductRepository;
import com.supermarket.backend.product.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierProductService {

    private final SupplierProductRepository supplierProductRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    public SupplierProductResponseDto create(SupplierProductCreateRequestDto dto) {

        if (supplierProductRepository
                .existsBySupplier_SupplierIdAndProduct_ProductId(
                        dto.getSupplierId(), dto.getProductId())) {
            throw new IllegalArgumentException("Supplier already linked to this product");
        }

        Supplier supplier = supplierRepository
                .findBySupplierIdAndIsActiveTrue(dto.getSupplierId())
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));

        Product product = productRepository
                .findByProductIdAndIsActiveTrue(dto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        SupplierProduct supplierProduct = new SupplierProduct();
        supplierProduct.setSupplier(supplier);
        supplierProduct.setProduct(product);
        supplierProduct.setPurchasePrice(dto.getPurchasePrice());
        supplierProduct.setIsActive(true);

        SupplierProduct saved = supplierProductRepository.save(supplierProduct);
        return toResponse(saved);
    }

    public List<SupplierProductResponseDto> getByProduct(Long productId) {
        return supplierProductRepository
                .findAllByProduct_ProductIdAndIsActiveTrue(productId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private SupplierProductResponseDto toResponse(SupplierProduct sp) {
        return SupplierProductResponseDto.builder()
                .supplierProductId(sp.getSupplierProductId())
                .supplierId(sp.getSupplier().getSupplierId())
                .supplierName(sp.getSupplier().getSupplierName())
                .productId(sp.getProduct().getProductId())
                .productName(sp.getProduct().getProductName())
                .purchasePrice(sp.getPurchasePrice())
                .isActive(sp.getIsActive())
                .build();
    }
}
