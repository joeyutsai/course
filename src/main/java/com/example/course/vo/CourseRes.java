package com.example.course.vo;

import com.example.course.entity.Course;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseRes {
	private String message;
	private Course course;
	
	public CourseRes() {

	}

	public CourseRes(String message) {
		this.message = message;
	}
	
	public CourseRes(Course course, String message) {
		this.course = course;
		this.message = message;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
