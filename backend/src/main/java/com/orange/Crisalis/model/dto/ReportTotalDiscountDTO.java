package com.orange.Crisalis.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportTotalDiscountDTO {
    private Long clientId;
    private String client;
    private String service;
    private Long orderNum;
    private Date date;
    private Double discount;
}
