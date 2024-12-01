package com.test.msig.teacher.demo.repository;

import com.test.msig.teacher.demo.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
