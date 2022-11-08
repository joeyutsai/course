package com.example.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.course.entity.Student;

@Repository
public interface StudentDao extends JpaRepository<Student, String>{

}
