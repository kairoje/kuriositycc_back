package com.kuriosity.kcc.service;

import com.kuriosity.kcc.exception.InformationNotFound;
import com.kuriosity.kcc.model.Order;
import com.kuriosity.kcc.model.User;
import com.kuriosity.kcc.repository.OrderRepository;
import com.kuriosity.kcc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Order createOrder(Order orderRequest) {
        User user = userRepository.findById(orderRequest.getUser().getId())
                .orElseThrow(() -> new InformationNotFound("User not found"));

        Order newOrder = new Order(user, orderRequest.getProducts(), orderRequest.getOrderDate(), orderRequest.getStatus());
        return orderRepository.save(newOrder);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new InformationNotFound("Order not found"));
    }

    @Override
    public Order updateOrder(Long orderId, Order updatedOrder) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new InformationNotFound("Order not found"));

        existingOrder.setProducts(updatedOrder.getProducts());
        existingOrder.setOrderDate(updatedOrder.getOrderDate());
        existingOrder.setStatus(updatedOrder.getStatus());

        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {

    }
}
