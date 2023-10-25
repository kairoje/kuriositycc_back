package com.kuriosity.kcc.repository;

import com.kuriosity.kcc.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findOrderById(Long orderId);
}
