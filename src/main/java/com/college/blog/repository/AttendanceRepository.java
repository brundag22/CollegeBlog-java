package com.college.blog.repository;
import com.college.blog.entity.Attendance;
import com.college.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudent(User student);
}