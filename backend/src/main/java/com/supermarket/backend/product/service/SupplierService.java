package com.supermarket.backend.product.service;

import com.supermarket.backend.product.dto.supplier.SupplierCreateRequestDto;
import com.supermarket.backend.product.dto.supplier.SupplierResponseDto;
import com.supermarket.backend.product.dto.supplier.SupplierUpdateRequestDto;
import com.supermarket.backend.product.entity.Supplier;
import com.supermarket.backend.product.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierResponseDto create(SupplierCreateRequestDto dto) {
        if (supplierRepository.existsBySupplierName(dto.getSupplierName())) {
            throw new IllegalArgumentException("Supplier already exists");
        }

        Supplier supplier = new Supplier();
        supplier.setSupplierName(dto.getSupplierName());
        supplier.setPhone(dto.getPhone());
        supplier.setEmail(dto.getEmail());
        supplier.setIsActive(true);

        Supplier saved = supplierRepository.save(supplier);
        return toResponse(saved);
    }

    public List<SupplierResponseDto> getAllActive() {
        return supplierRepository.findAllByIsActiveTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public SupplierResponseDto update(Long id, SupplierUpdateRequestDto dto) {
        Supplier supplier = supplierRepository.findBySupplierIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));

        supplier.setSupplierName(dto.getSupplierName());
        supplier.setPhone(dto.getPhone());
        supplier.setEmail(dto.getEmail());

        Supplier saved = supplierRepository.save(supplier);
        return toResponse(saved);
    }

    public void deactivate(Long id) {
        Supplier supplier = supplierRepository.findBySupplierIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));

        supplier.setIsActive(false);
        supplierRepository.save(supplier);
    }

    private SupplierResponseDto toResponse(Supplier supplier) {
        return SupplierResponseDto.builder()
                .supplierId(supplier.getSupplierId())
                .supplierName(supplier.getSupplierName())
                .phone(supplier.getPhone())
                .email(supplier.getEmail())
                .isActive(supplier.getIsActive())
                .build();
    }
}
