package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Lesson;
import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.entity.enums.UserType;
import com.example.studentmanagement.repository.LessonRepository;
import com.example.studentmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Value("${picture.upload.directory}")
    private String uploadDirectory;


    @GetMapping("/students")
    public String studentsPage(ModelMap modelMap) {
        List<User> students = userRepository.findAllByUserType(UserType.STUDENT);
        modelMap.addAttribute("students", students);
        return "students";
    }




    @GetMapping("/students/add")
    public String addStudentsPage(ModelMap modelMap) {
        modelMap.addAttribute("lessons", lessonRepository.findAll());
        return "addStudent";
    }

    @PostMapping("/students/add")
    public String addStudents(@ModelAttribute User user, @RequestParam int lessonId,
                              @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(uploadDirectory, picName);
            multipartFile.transferTo(file);
            user.setPicName(picName);
        }
        Optional<Lesson> byId = lessonRepository.findById(lessonId);
        if (byId.isPresent()) {
            Lesson lesson = byId.get();
            user.setLesson(lesson);
            userRepository.save(user);
        }
        return "redirect:/students";
    }



    @GetMapping("/student/update/{id}")
    public String updateStudentPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            modelMap.addAttribute("lessons", lessonRepository.findAll());
            modelMap.addAttribute("student", byId.get());
        } else {
            return "redirect:/students";
        }
        return "updateStudent";
    }


    @PostMapping("/student/update")
    public String updateStudent(@ModelAttribute User user,@RequestParam int lessonId,
                                 @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(uploadDirectory, picName);
            multipartFile.transferTo(file);
            user.setPicName(picName);
        } else {
            Optional<User> fromDB = userRepository.findById(user.getId());
            user.setPicName(fromDB.get().getPicName());
        }
        Optional<Lesson> byId = lessonRepository.findById(lessonId);
        if (byId.isPresent()){
            user.setLesson(byId.get());
        }
        userRepository.save(user);
        return "redirect:/students";
    }


    @GetMapping("/students/image/delete")
    public String deleteStudentImage(@RequestParam("id") int id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isEmpty()) {
            return "redirect:/students";
        } else {
            User user = byId.get();
            String picName = user.getPicName();
            if (picName != null) {
                user.setPicName(null);
                userRepository.save(user);
                File file = new File(uploadDirectory, picName);
                if (file.exists()) {
                    file.delete();
                }
            }
            return "redirect:/students/update/" + user.getId();
        }
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") int id){
        userRepository.deleteById(id);
        return "redirect:/students";
    }







    @GetMapping("/teachers")
    public String teachersPage(ModelMap modelMap) {
        List<User> teachers = userRepository.findAllByUserType(UserType.TEACHER);
        modelMap.addAttribute("teachers", teachers);
        return "teachers";
    }


    @GetMapping("/teachers/add")
    public String addTeachersPage(ModelMap modelMap) {
        modelMap.addAttribute("lessons", lessonRepository.findAll());
        return "addTeacher";
    }


    @PostMapping("/teachers/add")
    public String addTeachers(@ModelAttribute User user, @RequestParam int lessonId,
                              @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(uploadDirectory, picName);
            multipartFile.transferTo(file);
            user.setPicName(picName);
        }
        Optional<Lesson> byId = lessonRepository.findById(lessonId);
        if (byId.isPresent()) {
            Lesson lesson = byId.get();
            user.setLesson(lesson);
            userRepository.save(user);
        }
        return "redirect:/teachers";
    }



    @GetMapping("/teacher/update/{id}")
    public String updateTeacherPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            modelMap.addAttribute("lessons", lessonRepository.findAll());
            modelMap.addAttribute("teacher", byId.get());
        } else {
            return "redirect:/teachers";
        }
        return "updateTeacher";
    }



    @PostMapping("/teacher/update")
    public String updateTeacher(@ModelAttribute User user,@RequestParam int lessonId,
                                @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(uploadDirectory, picName);
            multipartFile.transferTo(file);
            user.setPicName(picName);
        } else {
            Optional<User> fromDB = userRepository.findById(user.getId());
            user.setPicName(fromDB.get().getPicName());
        }
        Optional<Lesson> byId = lessonRepository.findById(lessonId);
        if (byId.isPresent()){
            user.setLesson(byId.get());
        }
        userRepository.save(user);
        return "redirect:/teachers";
    }



    @GetMapping("/teachers/image/delete")
    public String deleteTeacherImage(@RequestParam("id") int id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isEmpty()) {
            return "redirect:/teachers";
        } else {
            User user = byId.get();
            String picName = user.getPicName();
            if (picName != null) {
                user.setPicName(null);
                userRepository.save(user);
                File file = new File(uploadDirectory, picName);
                if (file.exists()) {
                    file.delete();
                }
            }
            return "redirect:/teachers/update/" + user.getId();
        }
    }

    @GetMapping("/teachers/delete/{id}")
    public String deleteTeacher(@PathVariable("id") int id){
        userRepository.deleteById(id);
        return "redirect:/teachers";
    }
}
