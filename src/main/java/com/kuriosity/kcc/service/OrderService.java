package com.kuriosity.kcc.service;

import com.kuriosity.kcc.exception.InformationAlreadyExists;
import com.kuriosity.kcc.exception.InformationNotFound;
import com.kuriosity.kcc.model.Order;
import com.kuriosity.kcc.model.Product;
import com.kuriosity.kcc.model.User;
import com.kuriosity.kcc.repository.OrderRepository;
import com.kuriosity.kcc.repository.ProductRepository;
import com.kuriosity.kcc.security.AuthUserDetails;
import com.kuriosity.kcc.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) { this.orderRepository = orderRepository; }

    public static User getCurrentLoggedInUser(){
        AuthUserDetails authUserDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authUserDetails.getUser();
    }

    public List<Order> getOrders() {
        List<Order> orderList = orderRepository.findAll();
        if (orderList.isEmpty()){
            throw new InformationNotFound("No orders found");
        } else {
            return orderList;
        }
    }

    public Optional<Order> getOrder(Long orderId){
        Order order = orderRepository.findOrderById(orderId);
        if (order == null){
            throw new InformationNotFound("Order with id " + orderId + " doesn't exist");
        } else {
            return Optional.of(order);
        }
    }

    public Order createOrder(Order orderObject) {
        Order order = orderRepository.findOrderById(orderObject.getId());
        if (order != null) {
            throw new InformationAlreadyExists("Order: " + order + " already exist");
        } else {
            return orderRepository.save(orderObject);
        }
    }

    public Order updateOrder(Long orderId, Order orderObject) {

        Order order = orderRepository.findOrderById(orderId);

        if (order == null) {
            throw new InformationNotFound("Order with id " + orderId + " doesn't exist");
        } else {
            Order updatedOrder = orderRepository.findOrderById(orderId);
            updatedOrder.setDate(orderObject.getDate());
            updatedOrder.setTotal(orderObject.getTotal());
            updatedOrder.setStatus(orderObject.getStatus());
            return orderRepository.save(updatedOrder);
        }
    }

    public Optional<Order> deleteOrder(Long orderId) {
        Order order = orderRepository.findOrderById(orderId);
        if (order != null){
            orderRepository.deleteById(orderId);
            return Optional.of(order);
        } else {
            throw new InformationNotFound("Order with id " + orderId + " doesn't exist");
        }
    }

//    User based orders ==>

    public List<Order> getUserOrder(User user) {
        return orderRepository.findByUser(user);
    }

    public Order userCreateOrder(User user, Order order) {
        order.setUser(user);
        return orderRepository.save(order);
    }

// Products in orders ==>

    public Order addProductsToOrder(Long orderId, Long productId) {
        Order order = orderRepository.findOrderById(orderId);
        Product product = productRepository.findProductById(productId);
        order.getProducts().add(product);
        return orderRepository.save(order);
    }
}
