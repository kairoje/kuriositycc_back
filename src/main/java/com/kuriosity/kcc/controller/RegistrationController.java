package com.kuriosity.kcc.controller;

import com.kuriosity.kcc.model.User;
import com.kuriosity.kcc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public String registerUser(@RequestBody User user) {
        userRepository.save(user);
        return "User registered successfully!";
    }
}
