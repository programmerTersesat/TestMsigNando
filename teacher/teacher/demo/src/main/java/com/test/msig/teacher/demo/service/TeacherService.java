package com.test.msig.teacher.demo.service;

import com.test.msig.teacher.demo.dto.TeacherDTO;
import com.test.msig.teacher.demo.exception.ResourceNotFoundException;
import com.test.msig.teacher.demo.model.Teacher;
import com.test.msig.teacher.demo.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.io.ByteArrayOutputStream;
import java.util.List;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    // Get all teachers
    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get teacher by ID
    public TeacherDTO getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + id));
        return convertToDTO(teacher);
    }

    // Create teacher
    public TeacherDTO createTeacher(TeacherDTO teacherDTO) {
        Teacher teacher = convertToEntity(teacherDTO);
        Teacher savedTeacher = teacherRepository.save(teacher);
        return convertToDTO(savedTeacher);
    }

    // Update teacher
    public TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + id));
        teacher.setName(teacherDTO.getName());
        teacher.setSubject(teacherDTO.getSubject());
        teacher.setExperience(teacherDTO.getExperience());
        Teacher updatedTeacher = teacherRepository.save(teacher);
        return convertToDTO(updatedTeacher);
    }

    // Delete teacher
    public void deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new ResourceNotFoundException("Teacher not found with ID: " + id);
        }
        teacherRepository.deleteById(id);
    }

    // Convert Teacher entity to DTO
    private TeacherDTO convertToDTO(Teacher teacher) {
        TeacherDTO dto = new TeacherDTO();
        dto.setId(teacher.getId());
        dto.setName(teacher.getName());
        dto.setSubject(teacher.getSubject());
        dto.setExperience(teacher.getExperience());
        return dto;
    }

    // Convert DTO to Teacher entity
    private Teacher convertToEntity(TeacherDTO dto) {
        Teacher teacher = new Teacher();
        teacher.setName(dto.getName());
        teacher.setSubject(dto.getSubject());
        teacher.setExperience(dto.getExperience());
        return teacher;
    }

    public byte[] generateTeacherReport() {
        List<Teacher> teachers = teacherRepository.findAll();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream);
            Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

            document.add(new Paragraph("Teacher Report").setBold().setFontSize(16).setMarginBottom(20));

            Table table = new Table(new float[]{1, 3, 3, 2}); // Define column widths
            table.addCell("ID");
            table.addCell("Name");
            table.addCell("Subject");
            table.addCell("Experience");

            // Populate table with data
            for (Teacher teacher : teachers) {
                table.addCell(String.valueOf(teacher.getId()));
                table.addCell(teacher.getName());
                table.addCell(teacher.getSubject());
                table.addCell(String.valueOf(teacher.getExperience()));
            }

            // Add table to document
            document.add(table);

            document.close();

            return outputStream.toByteArray(); // Return PDF as byte array
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF report", e);
        }
    }
}
