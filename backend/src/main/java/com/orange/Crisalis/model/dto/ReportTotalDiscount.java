package com.orange.Crisalis.model.dto;


import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reportTotalDiscount") // <--- nombre del stored procedure en la base de datos
@Immutable
public class ReportTotalDiscount implements Serializable {

    @Column
    private Long clientId;
    @Id
    @Column(name = "order_num", updatable = false, nullable = false)
    private Long orderNum;
    @Column
    private String businessName;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String dtype;
    @Column
    private String serviceName;
    @Column
    private Date date;
    @Column
    private Double totalDiscount;

}
