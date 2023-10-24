package com.kuriosity.kcc.controller;

import com.kuriosity.kcc.exception.InformationNotFound;
import com.kuriosity.kcc.model.Order;
import com.kuriosity.kcc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create-order")
    public Order createOrder(@RequestBody Order orderRequest) {
        return orderService.createOrder(orderRequest);
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

