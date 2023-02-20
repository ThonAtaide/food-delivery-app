package com.example.fooddeliveryapp.model;


import com.example.fooddeliveryapp.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

import static com.example.fooddeliveryapp.enums.OrderStatus.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ORDER_TABLE")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CORRELATION_ID")
    private String correlationId;

    @Column(name = "PRODUCT")
    private String product;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "ORDER_STATUS")
    private OrderStatus orderStatus;

    public boolean orderStatusIsNotDelivered() {
        return !DELIVERED.equals(this.getOrderStatus());
    }
}
