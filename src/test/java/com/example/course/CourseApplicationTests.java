package com.example.course;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.course.entity.Course;
import com.example.course.entity.Student;
import com.example.course.ifs.CourseService;
import com.example.course.repository.CourseDao;
import com.example.course.repository.StudentDao;

@SpringBootTest
class CourseApplicationTests {
	@Autowired
	private CourseService courseService;

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private StudentDao studentDao;

	@Test
	public void withdrawCourseCodeTest() {
//		Set<String> listCode = new HashSet<>();
//		listCode.add("C001");
//		listCode.add("C002");
//		listCode.add("C003");
//		listCode.add("C004");
//		listCode.add("C005");
//		listCode.add("C006");
//		listCode.add("C007");
//		listCode.add("C008");
//		listCode.add("C009");
//		listCode.add("C010");
//		courseService.withdrawCourseCode("S101", listCode);	
	}


	@Test
	public void selectCourseCodeTest() {
//		Set<String> listCode = new TreeSet<>();
//		listCode.add("C001");
//		listCode.add("C002");
//		listCode.add("C003");
//		listCode.add("C004");
//		listCode.add("C005");
//		listCode.add("C006");
//		listCode.add("C007");
//		listCode.add("C008");
//		listCode.add("C009");
//		listCode.add("C010");
//		List<Course> courseListTest = courseDao.findAllByCodeIn(listCode);
//		courseService.selectCourseCode("S101", listCode);
//		System.out.println(studentDao.findById("S101").get().getSelectedCode());

	}

	@Test
	public void addStudentTest() {
//		courseService.addStudent("S101", "Will");
	}

	@Test
	public void addCourseTest() {
//		courseService.addCoures("C101", "courseTestName", 1, 9, 10, 1);
	}
	
	@Test
	public void addDefaultCourse() {
//		courseService.addCourse("C001", "English", 1, 9, 12, 3);
//		courseService.addCourse("C002", "Math", 2, 14, 16, 2);
//		courseService.addCourse("C003", "Art", 3, 8, 10, 2);
//		courseService.addCourse("C004", "History", 3, 10, 11, 1);
//		courseService.addCourse("C005", "Chemistry", 3, 13, 15, 2);
//		courseService.addCourse("C006", "Physics", 3, 9, 11, 2);
//		courseService.addCourse("C007", "Biology", 3, 10, 13, 3);
//		courseService.addCourse("C008", "Geography", 3, 11, 12, 2);
//		courseService.addCourse("C009", "PE", 3, 12, 14, 2);
//		courseService.addCourse("C010", "Chinese", 3, 12, 14, 2);
	}
	
	@Test
	public void addDefaultStudent() {
//		courseService.addStudent("S001", "Alex");
//		courseService.addStudent("S002", "Ben");
//		courseService.addStudent("S003", "Tom");
	}

}
