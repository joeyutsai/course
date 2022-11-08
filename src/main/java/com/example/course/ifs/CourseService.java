package com.example.course.ifs;

import java.util.Set;

import com.example.course.entity.Course;
import com.example.course.entity.Student;

public interface CourseService {
	public Course addCoures(String code, String name, int weekday, int startTime, int endTime, int credits);
	public Student addStudent(String id, String name);
	public Student selectCourseCode(String id, Set<String> listCode);
	public Student withdrawCourseCode(String id, Set<String> listCode);

}
