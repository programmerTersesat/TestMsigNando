package com.test.msig.student.demo.repository;


import com.test.msig.student.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByTeacherId(Long id);
}
