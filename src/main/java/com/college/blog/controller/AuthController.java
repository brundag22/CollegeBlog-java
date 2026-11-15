package com.college.blog.controller;

import com.college.blog.entity.User;
import com.college.blog.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // Show the login page
    @GetMapping({"/", "/login"})
    public String loginPage() {
        return "login"; 
    }

    // Process the login form
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        User user = userRepository.findByUsername(username);
        
        // Simple password check (plaintext for beginner project)
        if (user != null && user.getPassword().equals(password)) {
            // Save user in session
            session.setAttribute("loggedInUser", user);
            
            // Redirect based on role
            if ("TEACHER".equals(user.getRole())) {
                return "redirect:/teacher/dashboard";
            } else {
                return "redirect:/student/dashboard";
            }
        }
        
        // If login fails, show error on login page
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    // Handle logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Destroy the session
        return "redirect:/login";
    }
}