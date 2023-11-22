package com.orange.Crisalis.model;
import com.orange.Crisalis.model.dto.OrderDetailDTO;
import lombok.*;

import javax.persistence.*;

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
            allocationSize = 5
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_detail_sequence"
    )
    private Long id;
    private Double priceSell;
    private Integer quantity;
    @ManyToOne(
        fetch = FetchType.EAGER,
        optional = false
    )
    @JoinColumn(name = "sellable_good_id")
    private SellableGood sellableGood;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    private Double discount = .0;

    private Integer warrantyYear = 0;


    public OrderDetail(OrderDetailDTO orderDetailDTO) {
        this.id = orderDetailDTO.getId();
        this.priceSell = orderDetailDTO.getPriceSell();
        this.quantity = orderDetailDTO.getQuantity();
        this.sellableGood = orderDetailDTO.getSellableGood();

    }
}
