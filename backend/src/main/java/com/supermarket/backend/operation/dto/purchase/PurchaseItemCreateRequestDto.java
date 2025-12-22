package com.supermarket.backend.operation.dto.purchase;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseItemCreateRequestDto {

    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
    private Integer quantity;

    // opsiyonel: girilmezse null kalır
    private Double unitCost;
}
