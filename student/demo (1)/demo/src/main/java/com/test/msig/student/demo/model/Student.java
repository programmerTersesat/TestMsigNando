package com.test.msig.student.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    private String grade;

    private Long teacherId; // Reference to Teacher ID
}
