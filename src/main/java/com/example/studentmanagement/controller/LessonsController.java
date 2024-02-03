package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Lesson;
import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.entity.enums.UserType;
import com.example.studentmanagement.repository.LessonRepository;
import com.example.studentmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class LessonsController {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/lessons")
    public String lessonsPage(ModelMap modelMap) {
        List<Lesson> lessons = lessonRepository.findAll();
        modelMap.addAttribute("lessons", lessons);
        return "lessons";
    }


    @GetMapping("/lessons/add")
    public String addLessonPage(ModelMap modelMap) {
        modelMap.addAttribute("users", userRepository.findAllByUserType(UserType.TEACHER));
        return "addLesson";

    }


    @PostMapping("/lessons/add")
    public String addLesson(@ModelAttribute Lesson lesson,
                            @RequestParam int userId) {
        Optional<User> byId = userRepository.findById(userId);
        if(byId.isPresent()){
            User user = byId.get();
            lesson.setUser(user);
            lessonRepository.save(lesson);
        }
        return "redirect:/lessons";
    }


    @GetMapping("/lessons/delete/{id}")
    public String deleteLessons(@PathVariable("id") int id) {
        lessonRepository.deleteById(id);
        return "redirect:/lessons";
    }


    @GetMapping("/lessons/update/{id}")
    public String updateLessonsPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<Lesson> byId = lessonRepository.findById(id);
        if (byId.isPresent()) {
            modelMap.addAttribute("users", userRepository.findAllByUserType(UserType.TEACHER));
            modelMap.addAttribute("lesson",byId.get());
        }else {
            return "redirect:/lessons";
        }
        return "updateLesson";
    }



    @PostMapping("/lessons/update")
    public String updateLessons(@ModelAttribute Lesson lesson,@RequestParam int userId) throws IOException {
        Optional<User> byId = userRepository.findById(userId);
        if (byId.isPresent()){
            lesson.setUser(byId.get());
        }
        lessonRepository.save(lesson);
        return "redirect:/lessons";
    }
}
