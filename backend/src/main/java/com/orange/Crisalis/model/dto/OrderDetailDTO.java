package com.orange.Crisalis.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private Long id;
    private BigDecimal priceSell;
    private Integer quantity;
}
