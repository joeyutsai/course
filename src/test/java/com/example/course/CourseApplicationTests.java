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
		Set<String> listCode = new HashSet<>();
		listCode.add("C001");
		listCode.add("C002");
		listCode.add("C003");
		listCode.add("C004");
		listCode.add("C005");
		listCode.add("C006");
		listCode.add("C007");
		listCode.add("C008");
		listCode.add("C009");
		listCode.add("C010");

//		courseService.withdrawCourseCode("S101", listCode);	
	}

	@Test
	private boolean betweenExclude(int start1, int start2, int end1, int end2) {
		if (start2 > start1 && end2 <= end1) {
			System.out.println("Return: true");
			return true;
		}
		return (start1 >= start2 && start1 < end2) || (end1 > start2 && end1 <= end2);
	}

	@Test
	public void selectCourseCodeTest() {
		Set<String> listCode = new TreeSet<>();
		listCode.add("C001");
		listCode.add("C002");
//		listCode.add("C003");
//		listCode.add("C004");
//		listCode.add("C005");
//		listCode.add("C006");
//		listCode.add("C007");
//		listCode.add("C008");
//		listCode.add("C009");
//		listCode.add("C010");

		courseService.selectCourseCode("S101", listCode);
		System.out.println(studentDao.findById("S101").get().getSelectedCode());

	}

	@Test
	public void addStudentTest() {
//		courseService.addStudent("", "Tom");
	}

	@Test
	public void addCourseTest() {
//		courseService.addCoures("C100", "courseTestName", 1, 9, 10, 1);
	}

}
