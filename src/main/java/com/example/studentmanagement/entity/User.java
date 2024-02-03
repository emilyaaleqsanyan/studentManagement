package com.example.studentmanagement.entity;

import com.example.studentmanagement.entity.enums.UserType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String surname;
    private String email;
    private String picName;

    @ManyToOne
    @JoinColumn(name = "lessonId")
    private Lesson lesson;

    @Enumerated(EnumType.STRING)
    private UserType userType;

}
