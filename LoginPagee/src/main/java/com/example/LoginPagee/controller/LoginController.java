package com.example.LoginPagee.controller;

import com.example.LoginPagee.model.User;
import com.example.LoginPagee.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    // Home page - shows login form
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Login Page");
        return "index";
    }

    // Handle login (POST request) - Direct servlet-style approach
    @PostMapping("/login")
    public String handleLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {
        
        System.out.println("Login attempt: " + username + " / " + password);
        
        // Validate user (same as your servlet logic)
        User user = loginService.login(username, password);
        
        if (user != null) {
            // Login successful
            model.addAttribute("username", username);
            model.addAttribute("user", user);
            model.addAttribute("message", "Login successful!");
            return "welcome";
        } else {
            // Login failed
            model.addAttribute("error", "Invalid username or password!");
            model.addAttribute("username", username); // Keep username in form
            return "index";
        }
    }

    // Alternative method using @ModelAttribute
    @PostMapping("/login2")
    public String handleLogin2(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {
        
        boolean isValid = loginService.isValidUser(username, password);
        
        if (isValid) {
            User user = loginService.getUserByUsername(username);
            model.addAttribute("username", username);
            model.addAttribute("user", user);
            return "welcome";
        } else {
            model.addAttribute("error", "Invalid credentials!");
            return "index";
        }
    }

    // Registration page
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Handle registration
    @PostMapping("/register")
    public String handleRegistration(
            @RequestParam String name,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            Model model) {
        
        User newUser = new User();
        newUser.setName(name);
        newUser.setUsername(username);
        newUser.setPassword(password); // Note: In production, encrypt this!
        newUser.setEmail(email);
        
        boolean success = loginService.register(newUser);
        
        if (success) {
            model.addAttribute("success", "Registration successful! Please login.");
            return "index";
        } else {
            model.addAttribute("error", "Username already exists!");
            return "register";
        }
    }

    // Welcome page (protected)
    @GetMapping("/welcome")
    public String welcomePage(@RequestParam(required = false) String username, Model model) {
        if (username != null) {
            User user = loginService.getUserByUsername(username);
            model.addAttribute("user", user);
            model.addAttribute("username", username);
        }
        return "welcome";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(Model model) {
        model.addAttribute("message", "You have been logged out successfully.");
        return "index";
    }

    // API endpoint for AJAX login (optional)
    @PostMapping("/api/login")
    @ResponseBody
    public Map<String, Object> apiLogin(
            @RequestParam String username,
            @RequestParam String password) {
        
        Map<String, Object> response = new HashMap<>();
        User user = loginService.login(username, password);
        
        if (user != null) {
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("user", user);
        } else {
            response.put("success", false);
            response.put("message", "Invalid credentials");
        }
        
        return response;
    }
}