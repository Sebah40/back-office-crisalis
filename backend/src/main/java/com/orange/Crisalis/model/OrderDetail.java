package com.orange.Crisalis.model;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_detail")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    @Id
    @SequenceGenerator(
            name = "order_detail_sequence",
            sequenceName = "order_detail_sequence",
            allocationSize = 5,
            initialValue = 10
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_detail_sequence"
    )
    private Long id;
    private BigDecimal priceSell;
    private Double quantity;
    @ManyToOne(
        fetch = FetchType.EAGER,
        optional = false
    )
    @JoinColumn(name = "sellable_good_id")
    private SellableGood sellableGood;

}
