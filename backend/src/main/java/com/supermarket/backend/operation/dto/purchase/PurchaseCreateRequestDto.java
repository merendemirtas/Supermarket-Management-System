package com.supermarket.backend.operation.dto.purchase;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseCreateRequestDto {

    @NotNull
    private Long supplierId;

    private String note;

    @NotEmpty
    @Valid
    private List<PurchaseItemCreateRequestDto> items;
}
