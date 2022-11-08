package com.example.course.vo;

import java.util.Set;

public class CourseSelectedReq {
	private String id;
	private Set<String> listCode;

	public CourseSelectedReq() {

	}

	public CourseSelectedReq(String id, Set<String> listCode) {
		this.id = id;
		this.listCode = listCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<String> getListCode() {
		return listCode;
	}

	public void setListCode(Set<String> listCode) {
		this.listCode = listCode;
	}
	
	
}
