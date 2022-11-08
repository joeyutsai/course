package com.example.course.vo;

import javax.persistence.Id;

public class CourseReq {
	
	private String code;

	private String name;

	private int weekday;

	private int startTime;

	private int endTime;

	private int credits;

	public CourseReq() {

	}

	public CourseReq(String code, String name, int weekday, int startTime, int endTime, int credits) {
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

	public int getWeekday() {
		return weekday;
	}

	public void setWeekday(int weekday) {
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
