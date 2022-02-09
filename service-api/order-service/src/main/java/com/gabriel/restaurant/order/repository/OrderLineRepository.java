package com.gabriel.restaurant.order.repository;

import com.gabriel.restaurant.order.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

    @Query
    List<OrderLine> findAllByOrder(Long orderId);
}