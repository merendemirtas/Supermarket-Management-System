package com.supermarket.backend.operation.dto.sale;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleItemCreateRequestDto {

    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
    private Integer quantity;
}
