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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column
    private String business_name;
    @Column
    private String first_name;
    @Column
    private String last_name;
    @Column
    private String dtype;
    @Column
    private String item_name;
    @Column
    private Date date_created;
    @Column
    private Double discount;
}
