package com.example.course.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "course")
public class Course {
	@Id
	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "weekday")
	private String weekday;

	@Column(name = "start_time")
	private int startTime;

	@Column(name = "end_time")
	private int endTime;

	@Column(name = "credits")
	private int credits;

	public Course() {

	}

	public Course(String code, String name, String weekday, int startTime, int endTime, int credits) {
		this.code = code;
		this.name = name;
		this.weekday = weekday;
		this.startTime = startTime;
		this.endTime = endTime;
		this.credits = credits;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWeekday() {
		return weekday;
	}

	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

}
