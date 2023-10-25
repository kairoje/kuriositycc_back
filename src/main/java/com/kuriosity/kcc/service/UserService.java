package com.kuriosity.kcc.service;

import com.kuriosity.kcc.exception.InformationAlreadyExists;
import com.kuriosity.kcc.exception.InformationNotFound;
import com.kuriosity.kcc.model.Order;
import com.kuriosity.kcc.model.User;
import com.kuriosity.kcc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User register(User user) {
        return userRepository.save(user);
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            throw new InformationAlreadyExists("User with username " + username + " already exists");
        }
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new InformationNotFound("User with ID " + id + " not found"));
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User updatedUser) {
        if (userRepository.existsById(id)) {
            updatedUser.setAddress(updatedUser.getAddress());
            return userRepository.save(updatedUser);
        } else {
            throw new InformationNotFound("Username with id " + id + " not found");
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

//    public List<Order> getUserOrders(Long userId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new InformationNotFound("User not found"));
//        if (user != null) {
//            return user.getOrders();
//        } else {
//            throw new InformationNotFound("Order not found");
//        }
//    }
}
