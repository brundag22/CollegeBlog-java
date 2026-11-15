package com.college.blog.controller;

import com.college.blog.entity.Quiz;
import com.college.blog.entity.QuizResult;
import com.college.blog.entity.User;
import com.college.blog.repository.QuizRepository;
import com.college.blog.repository.QuizResultRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class QuizController {

    @Autowired private QuizRepository quizRepository;
    @Autowired private QuizResultRepository quizResultRepository;

    @GetMapping("/teacher/quizzes")
    public String teacherQuizzes(HttpSession session, Model model) {
        if (session.getAttribute("loggedInUser") == null) return "redirect:/login";
        model.addAttribute("quizzes", quizRepository.findAll());
        model.addAttribute("results", quizResultRepository.findAll());
        return "teacher-quizzes";
    }

    @PostMapping("/teacher/quizzes/create")
    public String createQuiz(Quiz quiz) {
        quizRepository.save(quiz);
        return "redirect:/teacher/quizzes";
    }

    @GetMapping("/student/quizzes")
    public String studentQuizzes(HttpSession session, Model model) {
        User student = (User) session.getAttribute("loggedInUser");
        if (student == null) return "redirect:/login";
        model.addAttribute("quizzes", quizRepository.findAll());
        model.addAttribute("results", quizResultRepository.findByStudent(student));
        return "student-quizzes";
    }

    @PostMapping("/student/quizzes/submit")
    public String submitQuiz(@RequestParam("quizId") Long quizId, @RequestParam("answer") String answer, HttpSession session) {
        User student = (User) session.getAttribute("loggedInUser");
        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if (student != null && quiz != null) {
            QuizResult result = new QuizResult();
            result.setQuiz(quiz);
            result.setStudent(student);
            result.setScore(quiz.getCorrectOption().equalsIgnoreCase(answer) ? 100 : 0);
            quizResultRepository.save(result);
        }
        return "redirect:/student/quizzes";
    }
}