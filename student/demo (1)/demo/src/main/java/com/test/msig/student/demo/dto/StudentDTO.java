package com.test.msig.student.demo.dto;

import lombok.Data;

@Data
public class StudentDTO {
    private String name;
    private int age;
    private String grade;
    private Long teacherId;
    // Reference to Teacher ID
}
