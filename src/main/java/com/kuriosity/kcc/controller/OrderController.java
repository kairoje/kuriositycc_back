package com.kuriosity.kcc.controller;

import com.kuriosity.kcc.model.Order;
import com.kuriosity.kcc.model.Product;
import com.kuriosity.kcc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public void setProductService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/orders/{orderId}")
    public Optional<Order> getOrder(@PathVariable(value = "orderId") Long orderId) {
        return orderService.getOrder(orderId);
    }

    @PostMapping
    @ResponseBody
    public Order addNewOrder(@RequestBody Order order) {
        return orderService.addNewOrder(order);
    }

    @DeleteMapping(path = "{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }

}

