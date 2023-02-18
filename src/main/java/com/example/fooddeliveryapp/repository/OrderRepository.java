package com.example.fooddeliveryapp.repository;

import com.example.fooddeliveryapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
