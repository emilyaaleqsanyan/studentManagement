package com.example.studentmanagement.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@Table(name = "lesson")
@ToString(exclude = "user")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private double duration;
    private double price;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start_date;
    @ManyToOne
    private User user;
}