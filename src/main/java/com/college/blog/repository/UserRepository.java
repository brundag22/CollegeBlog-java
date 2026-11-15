package com.college.blog.repository;

import com.college.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // This allows us to find a user by their username for login
    User findByUsername(String username);
}