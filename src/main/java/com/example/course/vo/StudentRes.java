package com.example.course.vo;

import java.util.Set;

import com.example.course.entity.Course;
import com.example.course.entity.Student;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentRes {
	private String message;
	private Student student;
	private Set<Course> selectedCourse;

	public StudentRes() {

	}

	public StudentRes(String message) {
		this.message = message;
	}

	public StudentRes(Student student, String message) {
		this.student = student;
		this.message = message;
	}

	public StudentRes(Student student, Set<Course> selectedCourse) {
		this.student = student;
		this.selectedCourse = selectedCourse;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Set<Course> getSelectedCourse() {
		return selectedCourse;
	}

	public void setSelectedCourse(Set<Course> selectedCourse) {
		this.selectedCourse = selectedCourse;
	}

}
