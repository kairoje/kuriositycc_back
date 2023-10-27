package com.kuriosity.kcc.service;

import com.kuriosity.kcc.exception.InformationAlreadyExists;
import com.kuriosity.kcc.exception.InformationNotFound;
import com.kuriosity.kcc.model.LoginRequest;
import com.kuriosity.kcc.model.Order;
import com.kuriosity.kcc.model.User;
import com.kuriosity.kcc.repository.UserRepository;
import com.kuriosity.kcc.security.AuthUserDetails;
import com.kuriosity.kcc.security.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class responsible for managing user-related operations such as registration,
 * login, retrieval, update, and deletion of user information.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    /**
     * Constructor for UserService.
     *
     * @param userRepository The repository for user data.
     * @param passwordEncoder A component for encoding and decoding passwords.
     * @param jwtUtils A utility for handling JSON Web Tokens (JWT).
     * @param authenticationManager An authentication manager for user authentication.
     */
    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder,
                       JWTUtils jwtUtils,
                       @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }


    /**
     * Registers a new user by saving their information to the database.
     *
     * @param user The user to be registered.
     * @return The registered user after successful registration.
     * @throws InformationAlreadyExists If a user with the same email address already exists.
     */
    public User register(User user) {
        if (!userRepository.existsByUsername(user.getUsername())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } else {
            throw new InformationAlreadyExists("User with email address " + user.getUsername() + " already exists");
        }
    }

    /**
     * Logs in a user with the provided username and password, returning a JWT token upon successful authentication.
     *
     * @param loginRequest The login request containing the username and password.
     * @return An optional JWT token if login is successful, empty if login fails.
     */
    public Optional<String> login(LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        try{
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            AuthUserDetails authUserDetails = (AuthUserDetails) authentication.getPrincipal();
            return Optional.of(jwtUtils.generateJwtToken(authUserDetails));
        } catch (Exception e){
            return Optional.empty();
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


    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}
