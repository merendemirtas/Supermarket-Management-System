package com.supermarket.backend.operation.dto.sale;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleResponseDto {
    private Long saleId;
    private String cashierUsername;
    private LocalDateTime saleDate;
    private Double totalAmount;
    private String note;
    private List<SaleItemResponseDto> items;
}
