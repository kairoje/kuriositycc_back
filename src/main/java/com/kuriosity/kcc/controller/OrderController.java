package com.kuriosity.kcc.controller;

import com.kuriosity.kcc.exception.InformationNotFound;
import com.kuriosity.kcc.model.Order;
import com.kuriosity.kcc.model.User;
import com.kuriosity.kcc.repository.OrderRepository;
import com.kuriosity.kcc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class OrderController {

    private UserRepository userRepository;

    private OrderRepository orderRepository;

    @PostMapping
    public Order createOrder(@RequestBody Order orderRequest) {
        User user = userRepository.findById(orderRequest.getUser().getId())
                .orElseThrow(() -> new InformationNotFound("User not found"));

        Order newOrder = new Order(orderRequest.getId(), orderRequest.getUser(), orderRequest.getProducts(), orderRequest.getOrderDate(), orderRequest.getOrderTotal(), orderRequest.getStatus());
        return orderRepository.save(newOrder);
    }
}
