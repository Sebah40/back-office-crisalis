package com.orange.Crisalis.model.dto.filters;

import com.orange.Crisalis.enums.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFilter {

    private String clientName;

    @Enumerated(value = EnumType.STRING)
    private List<OrderState> state;

    private String dtype;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fromDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate untilDate;

    public OrderFilter(String clientName) {
        this.clientName = clientName;
    }

}
