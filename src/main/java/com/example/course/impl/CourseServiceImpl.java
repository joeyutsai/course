package com.example.course.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course.ifs.CourseService;
import com.example.course.repository.CourseDao;

@Service
public class CourseServiceImpl implements CourseService {
	@Autowired
	private CourseDao courseDao;

}
