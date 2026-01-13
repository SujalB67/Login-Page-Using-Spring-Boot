package com.example.LoginPagee.repository;

import com.example.LoginPagee.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Method to authenticate user (same as your servlet logic)
    public User authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try {
            List<User> users = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(User.class),
                username, password
            );
            
            return users.isEmpty() ? null : users.get(0);
            
        } catch (Exception e) {
            System.err.println("Error in authentication: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Alternative method - just check if user exists
    public boolean validateUser(String username, String password) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
        
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, password);
            return count != null && count > 0;
        } catch (Exception e) {
            System.err.println("Error validating user: " + e.getMessage());
            return false;
        }
    }

    // Get user by username only
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        
        try {
            List<User> users = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(User.class),
                username
            );
            
            return users.isEmpty() ? null : users.get(0);
        } catch (Exception e) {
            System.err.println("Error finding user: " + e.getMessage());
            return null;
        }
    }

    // Insert new user (for registration)
    public int save(User user) {
        String sql = "INSERT INTO users (username, password, name, email) VALUES (?, ?, ?, ?)";
        
        try {
            return jdbcTemplate.update(
                sql,
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getEmail()
            );
        } catch (Exception e) {
            System.err.println("Error saving user: " + e.getMessage());
            return 0;
        }
    }
}