package com.kuriosity.kcc.repository;

import com.kuriosity.kcc.model.Order;
import com.kuriosity.kcc.model.Product;
import com.kuriosity.kcc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findOrderById(Long orderId);

    List<Order> findByUser(User user);

    List<Order> findByProductsIn(List<Product> productsList);
}
