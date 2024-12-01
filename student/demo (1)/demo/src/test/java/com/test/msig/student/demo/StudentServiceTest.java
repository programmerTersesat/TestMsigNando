package com.test.msig.student.demo;

import com.google.gson.Gson;

import com.test.msig.student.demo.dto.StudentDTO;
import com.test.msig.student.demo.dto.TeacherDTO;
import com.test.msig.student.demo.exception.ResourceNotFoundException;
import com.test.msig.student.demo.model.Student;
import com.test.msig.student.demo.repository.StudentRepository;
import com.test.msig.student.demo.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Gson gson;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllStudents() {
        // Mock data
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Alice");
        student1.setAge(15);
        student1.setGrade("10th");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Bob");
        student2.setAge(16);
        student2.setGrade("11th");

        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));

        List<StudentDTO> result = studentService.getAllStudents();

        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testGetStudentById() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Alice");
        student.setAge(15);
        student.setGrade("10th");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        StudentDTO result = studentService.getStudentById(1L);

        assertNotNull(result);
        assertEquals("Alice", result.getName());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetStudentById_NotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> studentService.getStudentById(1L));
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateStudent() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Alice");
        studentDTO.setAge(15);
        studentDTO.setGrade("10th");

        Student student = new Student();
        student.setId(1L);
        student.setName("Alice");
        student.setAge(15);
        student.setGrade("10th");

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        StudentDTO result = studentService.createStudent(studentDTO);

        assertNotNull(result);
        assertEquals("Alice", result.getName());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testUpdateStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Alice");
        student.setAge(15);
        student.setGrade("10th");

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Bob");
        studentDTO.setAge(16);
        studentDTO.setGrade("11th");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        StudentDTO result = studentService.updateStudent(1L, studentDTO);

        assertNotNull(result);
        assertEquals("Bob", result.getName());
        assertEquals(16, result.getAge());
        assertEquals("11th", result.getGrade());
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testDeleteStudent() {
        when(studentRepository.existsById(1L)).thenReturn(true);

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).existsById(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteStudent_NotFound() {
        when(studentRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> studentService.deleteStudent(1L));
        verify(studentRepository, times(1)).existsById(1L);
    }

}
