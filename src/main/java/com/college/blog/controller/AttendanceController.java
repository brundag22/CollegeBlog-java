package com.college.blog.controller;

import com.college.blog.entity.Attendance;
import com.college.blog.entity.User;
import com.college.blog.repository.AttendanceRepository;
import com.college.blog.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AttendanceController {

    @Autowired private AttendanceRepository attendanceRepository;
    @Autowired private UserRepository userRepository;

    @GetMapping("/teacher/attendance")
    public String teacherAttendance(HttpSession session, Model model) {
        if (session.getAttribute("loggedInUser") == null) return "redirect:/login";
        model.addAttribute("students", userRepository.findByRole("STUDENT"));
        model.addAttribute("records", attendanceRepository.findAll());
        return "teacher-attendance";
    }

    @PostMapping("/teacher/attendance/mark")
    public String markAttendance(@RequestParam("studentId") Long studentId, @RequestParam("status") String status) {
        User student = userRepository.findById(studentId).orElse(null);
        if (student != null) {
            Attendance attendance = new Attendance();
            attendance.setStudent(student);
            attendance.setStatus(status);
            attendanceRepository.save(attendance);
        }
        return "redirect:/teacher/attendance";
    }

    @GetMapping("/student/attendance")
    public String studentAttendance(HttpSession session, Model model) {
        User student = (User) session.getAttribute("loggedInUser");
        if (student == null) return "redirect:/login";
        model.addAttribute("records", attendanceRepository.findByStudent(student));
        return "student-attendance";
    }
}