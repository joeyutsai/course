package com.example.course.constants;

public enum CourseRtnCode {
	SUCCESSFUL("200", "Successful"),
	REQ_REQUIRED("400", "Request cannot be empty."),
	CODE_REQUIRED("400", "Code cannot be null or empty"),
	NAME_REQUIRED("400", "Name cannot be null or empty"),
	WEEKDAY_FAILURE("400", "Range of weekday need to be 1~5"),
	COURSETIME_FAILURE("400","StartTime need to less than endTime"),
	COURSE_DUPLICATE("400","Course is already in the data base."),
	ID_REQUIRED("400","Id cannot be null or empty."),
	STUDENT_DUPLICATE("400","Student is already in the data base."),
	COURSE_SELECTED_FAILURE("400","Failures in Course Selection."),
	LISTCODE_REQUIRED("400","ListCode cannot be null or empty."),
	ID_NOT_IN_DB("400","The ID is not in the data base."),
	COURSE_WITHDRAW_FAILURE("400","Failures in Course Withdraw."),
	COURSE_UPDATE_FAILURE("400","Failures in Course Update."),
	STUDENT_UPDATE_FAILURE("400","Failures in Student Update.");
	
	private String code;
	
	private String message;
	
	private CourseRtnCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
}
