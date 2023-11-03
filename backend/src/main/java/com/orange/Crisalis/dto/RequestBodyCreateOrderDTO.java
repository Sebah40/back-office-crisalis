package com.orange.Crisalis.dto;

import lombok.*;

import java.util.List;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestBodyCreateOrderDTO {

    private Integer clientId;
    private List<ProductIdAndQuantityDTO> productIdList;


}
