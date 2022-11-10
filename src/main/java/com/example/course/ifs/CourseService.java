package com.example.course.ifs;

import java.util.Set;

import com.example.course.entity.Course;
import com.example.course.entity.Student;
import com.example.course.vo.StudentRes;

public interface CourseService {
	public Course addCourse(String code, String name, int weekday, int startTime, int endTime, int credits);

	public Course updateCourse(String code, String name, int weekday, int startTime, int endTime, int credits);

	public Student addStudent(String id, String name);

	public Student updateStudent(String id, String name);

	public Student selectCourseCode(String id, Set<String> listCode);

	public Student withdrawCourseCode(String id, Set<String> listCode);

	public StudentRes studentCourseInfo(String id);

}
