package com.gabriel.restaurant.order.repository;

import com.gabriel.restaurant.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}