package com.example.course.vo;

import com.example.course.entity.Student;

public class StudentRes {
	private String message;
	private Student student;
//	private Set<String> selectedCourse;

	public StudentRes() {

	}

	public StudentRes(String message) {
		this.message = message;
	}

	public StudentRes(Student student, String message) {
		this.student = student;
		this.message = message;
	}

//	public StudentRes(String message, Set<String> selectedCourse) {
//		this.message = message;
//		this.selectedCourse = selectedCourse;
//	}

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

//	public Set<String> getSelectedCourse() {
//		return selectedCourse;
//	}
//
//	public void setSelectedCourse(Set<String> selectedCourse) {
//		this.selectedCourse = selectedCourse;
//	}

}
