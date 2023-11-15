package com.orange.Crisalis.dto;


import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductIdAndQuantityDTO {
    private Long productId;
    private Integer quantity;
    private Integer warrantyYear;
}
