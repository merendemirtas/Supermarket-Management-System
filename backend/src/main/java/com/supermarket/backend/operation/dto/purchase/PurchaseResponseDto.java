package com.supermarket.backend.operation.dto.purchase;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseResponseDto {
    private Long purchaseId;
    private Long supplierId;
    private String supplierName;
    private String createdByUsername;
    private LocalDateTime purchaseDate;
    private String note;
    private List<PurchaseItemResponseDto> items;
}
