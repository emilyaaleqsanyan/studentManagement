package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson,Integer> {
}
