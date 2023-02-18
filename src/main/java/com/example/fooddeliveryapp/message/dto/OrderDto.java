package com.example.fooddeliveryapp.message.dto;

import com.example.fooddeliveryapp.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;

    private String correlationId;

    private String product;

    private BigDecimal price;

    private OrderStatus orderStatus;

}
