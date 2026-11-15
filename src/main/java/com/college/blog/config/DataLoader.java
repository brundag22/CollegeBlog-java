package com.college.blog.config;

import com.college.blog.entity.User;
import com.college.blog.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository) {
        return args -> {
            // Check if teacher exists, if not create one
            if (userRepository.findByUsername("teacher") == null) {
                User teacher = new User();
                teacher.setUsername("teacher");
                teacher.setPassword("pass123");
                teacher.setRole("TEACHER");
                userRepository.save(teacher);
                System.out.println("Teacher created: teacher / pass123");
            }

            // Check if student exists, if not create one
            if (userRepository.findByUsername("student") == null) {
                User student = new User();
                student.setUsername("student");
                student.setPassword("pass123");
                student.setRole("STUDENT");
                userRepository.save(student);
                System.out.println("Student created: student / pass123");
            }
        };
    }
}