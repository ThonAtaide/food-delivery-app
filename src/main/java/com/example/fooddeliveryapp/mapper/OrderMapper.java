package com.example.fooddeliveryapp.mapper;

import com.example.fooddeliveryapp.message.dto.OrderDto;
import com.example.fooddeliveryapp.model.Order;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper {

    Order from(OrderDto orderDto);

    OrderDto from(Order order);


}
