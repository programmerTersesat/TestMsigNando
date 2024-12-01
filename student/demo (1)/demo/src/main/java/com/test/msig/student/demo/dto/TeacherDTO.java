package com.test.msig.student.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeacherDTO {
    private Long id;
    private String name;
    private String subject;
    private int experience;
    private List<StudentDTO> students;
}
