package com.example.course.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.course.entity.Course;

@Repository
public interface CourseDao extends JpaRepository<Course, String> {
	public List<Course> findAllByCodeIn(Set<String> listCode);

}
