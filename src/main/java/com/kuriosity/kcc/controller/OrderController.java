package com.kuriosity.kcc.controller;

import com.kuriosity.kcc.exception.InformationNotFound;
import com.kuriosity.kcc.model.Order;
import com.kuriosity.kcc.model.User;
import com.kuriosity.kcc.repository.OrderRepository;
import com.kuriosity.kcc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;

@RestController
public class OrderController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/create-order")
    public Order createOrder(@RequestBody Order orderRequest) {

        User user = userRepository.findById(orderRequest.getUser().getId())
                .orElseThrow(() -> new InformationNotFound("User not found"));

        Order newOrder = new Order(user, orderRequest.getProducts(), orderRequest.getOrderDate(), orderRequest.getStatus());
        return orderRepository.save(newOrder);
    }

    @GetMapping("/orders/{orderId}")
    public Order getOrderById(@PathVariable Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new InformationNotFound("Order not found"));
    }

    @PutMapping("/orders/{orderId}")
    public Order updateOrder(@PathVariable Long orderId, @RequestBody Order updatedOrder) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new InformationNotFound("Order not found"));

        existingOrder.setProducts(updatedOrder.getProducts());
        existingOrder.setOrderDate(updatedOrder.getOrderDate());
        existingOrder.setStatus(updatedOrder.getStatus());

        return orderRepository.save(existingOrder);
    }

    @DeleteMapping("/orders/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new InformationNotFound("Order not found"));

        orderRepository.delete(order);
    }
}

