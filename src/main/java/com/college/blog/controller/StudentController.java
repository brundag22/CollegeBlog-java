package com.college.blog.controller;

import com.college.blog.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {

    @GetMapping("/student/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"STUDENT".equals(user.getRole())) {
            return "redirect:/login";
        }
        model.addAttribute("student", user);
        return "student-dashboard";
    }
}
