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

/**
 * Service class responsible for managing order-related operations, including retrieval,
 * creation, updating, and deletion of orders, as well as handling user-based orders and
 * adding products to orders.
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Setter for injecting the OrderRepository.
     *
     * @param orderRepository The repository for order data.
     */
    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) { this.orderRepository = orderRepository; }

    /**
     * Retrieves the currently logged-in user.
     *
     * @return The user who is currently logged in.
     */
    public static User getCurrentLoggedInUser(){
        AuthUserDetails authUserDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authUserDetails.getUser();
    }

    /**
     * Retrieves a list of all available orders.
     *
     * @return A list of orders if available, or throws an exception if no orders are found.
     * @throws InformationNotFound If no orders are found in the database.
     */
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
