package com.kuriosity.kcc.service;

import com.kuriosity.kcc.exception.InformationAlreadyExists;
import com.kuriosity.kcc.exception.InformationNotFound;
import com.kuriosity.kcc.model.Order;
import com.kuriosity.kcc.model.Product;
import com.kuriosity.kcc.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) { this.orderRepository = orderRepository; }

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

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
