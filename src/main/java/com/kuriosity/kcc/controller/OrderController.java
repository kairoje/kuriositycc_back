package com.kuriosity.kcc.controller;

import com.kuriosity.kcc.model.Order;
import com.kuriosity.kcc.model.Product;
import com.kuriosity.kcc.service.OrderService;
import com.kuriosity.kcc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class OrderController {

    private OrderService orderService;
    private ProductService productService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/orders")
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/orders/{orderId}")
    public Optional<Order> getOrder(@PathVariable(value = "orderId") Long orderId) {
        return orderService.getOrder(orderId);
    }

    @PostMapping("/orders")
    public Order createOrder(@RequestBody Order orderObject) {
        return orderService.createOrder(orderObject);
    }

    @PutMapping("/orders/{orderId}")
    public Order updateOrder(@PathVariable(value = "orderId") Long orderId, @RequestBody Order order) {
        return orderService.updateOrder(orderId, order);
    }

    @DeleteMapping("/orders/{orderId}")
    public Optional<Order> deleteOrder(@PathVariable(value = "orderId") Long orderId) {
        return orderService.deleteOrder(orderId);
    }

}

