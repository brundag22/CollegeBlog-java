package com.college.blog.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "quiz_results")
public class QuizResult {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne @JoinColumn(name = "quiz_id", nullable = false) private Quiz quiz;
    @ManyToOne @JoinColumn(name = "student_id", nullable = false) private User student;
    private int score; 

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Quiz getQuiz() { return quiz; }
    public void setQuiz(Quiz quiz) { this.quiz = quiz; }
    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
}