package com.kuriosity.kcc.controller;

import com.kuriosity.kcc.model.Order;
import com.kuriosity.kcc.model.User;
import com.kuriosity.kcc.service.OrderService;
import com.kuriosity.kcc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }


// User based order endpoints ==>

    @GetMapping("/{userId}/orders")
    public List<Order> getUserOrders(@PathVariable Long userId) {
        User user = userService.findById(userId);
        return orderService.getUserOrder(user);
    }

    @PostMapping("/{userId}/orders")
    public Order createOrderForUser(@PathVariable Long userId, @RequestBody Order order) {
        User user = userService.findById(userId);
        return orderService.userCreateOrder(user, order);
    }
}
