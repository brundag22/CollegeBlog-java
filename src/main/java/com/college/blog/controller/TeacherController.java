package com.college.blog.controller;

import com.college.blog.entity.Note;
import com.college.blog.entity.User;
import com.college.blog.repository.NoteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private NoteRepository noteRepository;

    // Show the Teacher Dashboard
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"TEACHER".equals(user.getRole())) return "redirect:/login";
        
        model.addAttribute("teacher", user);
        return "teacher-dashboard";
    }

    // Show the Notes Management Page
    @GetMapping("/notes")
    public String manageNotes(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"TEACHER".equals(user.getRole())) return "redirect:/login";

        // Fetch all notes from the database and pass to HTML
        List<Note> notes = noteRepository.findAll();
        model.addAttribute("notes", notes);
        
        return "teacher-notes";
    }

    // Handle Note File Upload
    @PostMapping("/notes/upload")
    public String uploadNote(@RequestParam("title") String title,
                             @RequestParam("file") MultipartFile file,
                             HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"TEACHER".equals(user.getRole())) return "redirect:/login";

        try {
            // Define the path to the uploads folder
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            
            // Save the physical file
            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Save the record in the MySQL Database
            Note note = new Note();
            note.setTitle(title);
            note.setFileName(fileName);
            note.setTeacher(user);
            noteRepository.save(note);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Refresh the page
        return "redirect:/teacher/notes";
    }
}