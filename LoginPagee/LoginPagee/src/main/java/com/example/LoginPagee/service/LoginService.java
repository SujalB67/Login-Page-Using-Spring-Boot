package com.example.LoginPagee.service;

import com.example.LoginPagee.model.User;
import com.example.LoginPagee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    // Direct authentication (same as servlet)
    public User login(String username, String password) {
        return userRepository.authenticate(username, password);
    }

    // Check if user exists
    public boolean isValidUser(String username, String password) {
        return userRepository.validateUser(username, password);
    }

    // Register new user
    public boolean register(User user) {
        // Check if username already exists
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            return false; // Username already taken
        }
        
        // Save new user
        int result = userRepository.save(user);
        return result > 0;
    }

    // Get user details
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}