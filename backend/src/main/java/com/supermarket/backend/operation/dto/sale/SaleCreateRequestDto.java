package com.supermarket.backend.operation.dto.sale;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleCreateRequestDto {

    private String note;

    @NotEmpty
    @Valid
    private List<SaleItemCreateRequestDto> items;
}
