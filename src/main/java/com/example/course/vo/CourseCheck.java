package com.example.course.vo;

public class CourseCheck {
	private boolean checkResult;
	private Integer credits;
	private String message;
	private String studentListCodeForDB;

	public CourseCheck() {

	}

//	public CourseCheck(Integer credits) {
//		this.credits = credits;
//	}
//
//	public CourseCheck(Integer credits, String message, String studentListCodeForDB) {
//		this.credits = credits;
//		this.message = message;
//		this.studentListCodeForDB = studentListCodeForDB;
//	}

	public Integer getCredits() {
		return credits;
	}

	public void setCredits(Integer credits) {
		this.credits = credits;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStudentListCodeForDB() {
		return studentListCodeForDB;
	}

	public void setStudentListCodeForDB(String studentListCodeForDB) {
		this.studentListCodeForDB = studentListCodeForDB;
	}

	public boolean isCheckResult() {
		return checkResult;
	}

	public void setCheckResult(boolean checkResult) {
		this.checkResult = checkResult;
	}


}
