package com.test.msig.student.demo.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.test.msig.student.demo.dto.StudentDTO;
import com.test.msig.student.demo.dto.TeacherDTO;
import com.test.msig.student.demo.exception.ResourceNotFoundException;
import com.test.msig.student.demo.model.Student;
import com.test.msig.student.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final RestTemplate restTemplate;
    private final Gson gson;

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        return convertToDTO(student);
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = convertToEntity(studentDTO);
        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);
    }

    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        student.setName(studentDTO.getName());
        student.setAge(studentDTO.getAge());
        student.setGrade(studentDTO.getGrade());
        student.setTeacherId(studentDTO.getTeacherId());
        Student updatedStudent = studentRepository.save(student);
        return convertToDTO(updatedStudent);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }

    public TeacherDTO getTeacherForStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentId));

        if (student.getTeacherId() == null) {
            throw new ResourceNotFoundException("No teacher assigned to student with ID: " + studentId);
        }

        String teacherServiceUrl = "http://localhost:8081/api/teachers/" + student.getTeacherId();
        String teacherJson = restTemplate.getForObject(teacherServiceUrl, String.class);

        Type teacherType = new TypeToken<TeacherDTO>() {}.getType();
        TeacherDTO teacherDTO = gson.fromJson(teacherJson, teacherType);

        List<Student> studentList = studentRepository.findByTeacherId(teacherDTO.getId());

        List<StudentDTO> studentDTOList = new ArrayList<>();

        for(Student student1 : studentList) {
            StudentDTO studentDTO = convertToDTO(student1);
            studentDTOList.add(studentDTO);
        }

        teacherDTO.setStudents(studentDTOList);
        return teacherDTO;
    }

    private StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setName(student.getName());
        dto.setAge(student.getAge());
        dto.setGrade(student.getGrade());
        dto.setTeacherId(student.getTeacherId());
        return dto;
    }

    private Student convertToEntity(StudentDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setAge(dto.getAge());
        student.setGrade(dto.getGrade());
        student.setTeacherId(dto.getTeacherId());
        return student;
    }
}
