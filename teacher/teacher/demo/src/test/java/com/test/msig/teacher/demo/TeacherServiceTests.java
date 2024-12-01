package com.test.msig.teacher.demo;

import com.test.msig.teacher.demo.dto.TeacherDTO;
import com.test.msig.teacher.demo.exception.ResourceNotFoundException;
import com.test.msig.teacher.demo.model.Teacher;
import com.test.msig.teacher.demo.repository.TeacherRepository;
import com.test.msig.teacher.demo.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTeachers() {
        // Mock data
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        teacher1.setName("John Doe");
        teacher1.setSubject("Mathematics");
        teacher1.setExperience(10);

        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setName("Jane Smith");
        teacher2.setSubject("Physics");
        teacher2.setExperience(8);

        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher1, teacher2));

        // Execute service method
        List<TeacherDTO> result = teacherService.getAllTeachers();

        // Assertions
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Smith", result.get(1).getName());
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    void testGetTeacherById() {
        // Mock data
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("John Doe");
        teacher.setSubject("Mathematics");
        teacher.setExperience(10);

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        // Execute service method
        TeacherDTO result = teacherService.getTeacherById(1L);

        // Assertions
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(teacherRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTeacherById_NotFound() {
        // Mock data
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        // Execute service method and assert exception
        assertThrows(ResourceNotFoundException.class, () -> teacherService.getTeacherById(1L));
        verify(teacherRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateTeacher() {
        // Mock data
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setName("John Doe");
        teacherDTO.setSubject("Mathematics");
        teacherDTO.setExperience(10);

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("John Doe");
        teacher.setSubject("Mathematics");
        teacher.setExperience(10);

        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

        // Execute service method
        TeacherDTO result = teacherService.createTeacher(teacherDTO);

        // Assertions
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(teacherRepository, times(1)).save(any(Teacher.class));
    }

    @Test
    void testUpdateTeacher() {
        // Mock data
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("John Doe");
        teacher.setSubject("Mathematics");
        teacher.setExperience(10);

        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setName("Jane Doe");
        teacherDTO.setSubject("Physics");
        teacherDTO.setExperience(12);

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

        // Execute service method
        TeacherDTO result = teacherService.updateTeacher(1L, teacherDTO);

        // Assertions
        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        assertEquals("Physics", result.getSubject());
        assertEquals(12, result.getExperience());
        verify(teacherRepository, times(1)).findById(1L);
        verify(teacherRepository, times(1)).save(any(Teacher.class));
    }

    @Test
    void testDeleteTeacher() {
        // Mock data
        when(teacherRepository.existsById(1L)).thenReturn(true);

        // Execute service method
        teacherService.deleteTeacher(1L);

        // Verify
        verify(teacherRepository, times(1)).existsById(1L);
        verify(teacherRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTeacher_NotFound() {
        // Mock data
        when(teacherRepository.existsById(1L)).thenReturn(false);

        // Execute service method and assert exception
        assertThrows(ResourceNotFoundException.class, () -> teacherService.deleteTeacher(1L));
        verify(teacherRepository, times(1)).existsById(1L);
    }
}
