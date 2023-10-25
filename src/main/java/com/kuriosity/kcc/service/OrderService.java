package com.kuriosity.kcc.service;

import com.kuriosity.kcc.model.Order;
import com.kuriosity.kcc.repository.OrderRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) { this.orderRepository = orderRepository; }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order addNewOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
