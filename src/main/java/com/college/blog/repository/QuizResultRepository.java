package com.college.blog.repository;
import com.college.blog.entity.QuizResult;
import com.college.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    List<QuizResult> findByStudent(User student);
}