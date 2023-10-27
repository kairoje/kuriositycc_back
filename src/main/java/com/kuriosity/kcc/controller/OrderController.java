package com.kuriosity.kcc.controller;

import com.kuriosity.kcc.model.Order;
import com.kuriosity.kcc.model.Product;
import com.kuriosity.kcc.service.OrderService;
import com.kuriosity.kcc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
        Optional<Order> order = orderService.getOrder(orderId);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody Order order) {
        Order updatedOrder = orderService.updateOrder(orderId, order);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Long orderId) {
        Optional<Order> deletedOrder = orderService.deleteOrder(orderId);
        return deletedOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{orderId}/add-product/{productId}")
    public ResponseEntity<Order> addProductToOrder(@PathVariable Long orderId, @PathVariable Long productId) {
        Order updatedOrder = orderService.addProductsToOrder(orderId, productId);
        return ResponseEntity.ok(updatedOrder);
    }

}

