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
	public void withdrawCourseTest() {
		Set<String> listCode = new HashSet<>();
		listCode.add("C001");
		listCode.add("C002");
		listCode.add("C003");
		listCode.add("C004");
		listCode.add("C005");
		listCode.add("C010");

		
		courseService.withdrawCourseCode("S101", listCode);
		
	}
	
//	@Test
//	public void withdrawCourseCheckIMPL() {
//		// student DB studentListCode
//		Set<String> studentListCode = new HashSet<>();
//		studentListCode.add("C001");
//		studentListCode.add("C002");
//		studentListCode.add("C003");
////		studentListCode.add("C004");
////		studentListCode.add("C005");
//
//		// input withdraw course (listCode)
//		Set<String> listCode = new HashSet<>();
//		listCode.add("C001");
////		listCode.add("C002");
////		listCode.add("C003");
////		listCode.add("C004");
//		
//		String[] message = { null, null };
//		// 確認輸入的 code是否存在於 courseDB中，若存在則將其 code放入 listCodeCheck
//		Set<String> listCodeCheck = new HashSet<>();
//		for (String listCodeItem : listCode) {
//			Optional<Course> courseOp = courseDao.findById(listCodeItem);
//			if (courseOp.isPresent()) {
//				listCodeCheck.add(listCodeItem);
//			}
//		}
//
//		// listCodeCheck與 studentListCode做比對。
//		// 比對後，將相同的 code放入 sameListCode
//		Set<String> sameListCode = new HashSet<>();
//		for (String item : listCodeCheck) {
//			for (String studentItem : studentListCode) {
//				if (item.equalsIgnoreCase(studentItem)) {
//					sameListCode.add(item);
//				}
//			}
//		}
//		// 去除 studentListCode中 sameListCode的所有項目。
//		for (String item : sameListCode) {
//			studentListCode.remove(item);
//		}
//		// studentListCode 去比對courseDB中的資料並取出，再做studentDB selectedCode & credits的更新
//		Set<String> newListCode = new HashSet<>();
//		int totCredits = 0;
//
//		for (String listStudentCodeItem : studentListCode) {
//			Optional<Course> courseOp = courseDao.findById(listStudentCodeItem);
//			if (courseOp.isPresent()) {
//				newListCode.add(listStudentCodeItem);
//				totCredits += courseOp.get().getCredits();
//			}
//			message[0] = String.valueOf(totCredits);
//			message[1] = newListCode.toString();
//		}
//
//		System.out.println("TotCredits: " + message[0]);
//		System.out.println("StudentListCode: " + message[1]);
//
////		return message;
//	}
//	
//	@Test
//	public void withdrawCourseCheckTest() {
//		
//		// student DB studentListCode
//		Set<String> studentListCode = new HashSet<>();
//		studentListCode.add("C001");
//		studentListCode.add("C002");
//		studentListCode.add("C003");
//		studentListCode.add("C004");
//		studentListCode.add("C005");
//
//		// input withdraw course (listCode)
//		Set<String> listCode = new HashSet<>();
//		listCode.add("C001");
//		listCode.add("C002");
//		listCode.add("C003");
//		listCode.add("C004");
////		listCode.add("C005");
////		listCode.add("C006");
////		listCode.add("C007");
////		listCode.add("C008");
////		listCode.add("C009");
////		listCode.add("C010");
//
//		// 確認輸入的 code是否存在於 courseDB中，若存在則將其 code放入 listCodeCheck
//		Set<String> listCodeCheck = new HashSet<>();
//		for (String listCodeItem : listCode) {
//			Optional<Course> courseOp = courseDao.findById(listCodeItem);
//			if (courseOp.isPresent()) {
//				listCodeCheck.add(listCodeItem);
//			}
//		}
//
//		// listCodeCheck與 studentListCode做比對。
//		// 比對後，將相同的 code放入 sameListCode
//		Set<String> sameListCode = new HashSet<>();
//		for (String item : listCodeCheck) {
//			for (String studentItem : studentListCode) {
//				if (item.equalsIgnoreCase(studentItem)) {
//					sameListCode.add(item);
//				}
//			}
//		}
//		// 去除 studentListCode中 sameListCode的所有項目。
//		for (String item : sameListCode) {
//			studentListCode.remove(item);
//		}
//		// studentListCode 去比對courseDB中的資料並取出，再做studentDB selectedCode & credits的更新
//		Set<String> newListCode = new HashSet<>();
//		int totCredits = 0;
//
//		for (String listStudentCodeItem : studentListCode) {
//			Optional<Course> courseOp = courseDao.findById(listStudentCodeItem);
//			if (courseOp.isPresent()) {
//				newListCode.add(listStudentCodeItem);
//				totCredits += courseOp.get().getCredits();
//			}
//		}
//
//		System.out.println("StudentListCode: " + studentListCode.toString());
//		System.out.println("TotCredits: " + totCredits);
//
//	}
//
//	@Test
//	public void selectCourseCheckTest() {
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
//
//		// credits limit
//		int totCredits = 0;
//		// (findAll)
////		List<Course> listCourse = courseDao.findAll();
////		for (String listCodeItem : listCode) {
////			for (Course courseItem : listCourse) {
////				if (listCodeItem.equalsIgnoreCase(courseItem.getCode())) {
////					totCredits += courseItem.getCredits();
////				}
////			}
////		}
//		// (findById)
//		List<Course> listCourse = new ArrayList<>();
//		for (String listCodeItem : listCode) {
//			Optional<Course> courseOp = courseDao.findById(listCodeItem);
//			if (courseOp.isPresent()) {
//				listCourse.add(courseOp.get());
//				totCredits += courseOp.get().getCredits();
//			}
//		}
//
//		System.out.println("TotCredits: " + totCredits);
//
//		if (totCredits > 10) {
//			System.out.println("ERROR. Limit: 10 credits   TotCredits: " + totCredits);
//		}
//
//		// course time
//		// weektime check
//		for (Course item : listCourse) {
//			for (int i = 0; i < listCourse.size(); i++) {
//				if (item.getWeekday() == listCourse.get(i).getWeekday()) {
//					boolean checkCourseTime;
////					System.out.println("Same weekday");
////					System.out.println("Course Item: " + item.getCode() + " Course weekday:" + item.getWeekday());
////					System.out.println("ListCourse Item: " + listCourse.get(i).getCode() + " ListCourse weekday:"
////							+ listCourse.get(i).getWeekday());
//
//					if (!item.getCode().equalsIgnoreCase(listCourse.get(i).getCode())) {
//						if (listCourse.get(i).getStartTime() == item.getStartTime()
//								&& item.getEndTime() == listCourse.get(i).getEndTime()) {
//							checkCourseTime = true;
//							System.out.println("衝堂：完全在同一時間");
//							System.out.println("Course Item: " + item.getCode() + " Course weekday:" + item.getWeekday()
//									+ " Course startTime:" + item.getStartTime() + " Course endTime:"
//									+ item.getEndTime());
//							System.out.println("ListCourse Item: " + listCourse.get(i).getCode()
//									+ " ListCourse weekday:" + listCourse.get(i).getWeekday() + " ListCourse startTime:"
//									+ listCourse.get(i).getStartTime() + " ListCourse endTime:"
//									+ listCourse.get(i).getEndTime());
//						}
//
//						if ((listCourse.get(i).getStartTime() < item.getStartTime()
//								&& item.getStartTime() < listCourse.get(i).getEndTime())
//								|| (listCourse.get(i).getStartTime() < item.getEndTime()
//										&& item.getEndTime() < listCourse.get(i).getEndTime())) {
//							checkCourseTime = true;
//							System.out.println("衝堂：開始或結束的時間在其範圍內");
//							System.out.println("Course Item: " + item.getCode() + " Course weekday:" + item.getWeekday()
//									+ " Course startTime:" + item.getStartTime() + " Course endTime:"
//									+ item.getEndTime());
//							System.out.println("ListCourse Item: " + listCourse.get(i).getCode()
//									+ " ListCourse weekday:" + listCourse.get(i).getWeekday() + " ListCourse startTime:"
//									+ listCourse.get(i).getStartTime() + " ListCourse endTime:"
//									+ listCourse.get(i).getEndTime());
//						}
//
//					}
//				}
//			}
//
//		}
//
//	}
//
//	@Test
//	public void selectCourseCodeTest() {
//		Set<String> listCode = new TreeSet<>();
//		listCode.add("C001");
//		listCode.add("C002");
//		listCode.add("C003");
//		listCode.add("C004");
////		listCode.add("C005");
////		listCode.add("C006");
////		listCode.add("C007");
////		listCode.add("C008");
////		listCode.add("C009");
//		listCode.add("C010");
//
//		System.out.println("Test TreeSet listCode: " + listCode.toString());
//		courseService.selectCourseCode("S101", listCode);
//
////		courseService.selectCourseCode("S099", "C002, C003");
////		Student test1 = new Student("S101", "JJ");
////		studentDao.save(test1);
////		test1.setSelectedCode("C001, C002");
////		test1.setCredits(5);
////		studentDao.save(test1);
////		studentDao.findById("S101");
//	}
//
//	@Test
//	public void addStudentTest() {
////		courseService.addStudent("", "Tom");
//	}
//
//	@Test
//	public void addCourseTest() {
////		courseService.addCoures("002code", "courseName", 1, 9, 10, 1);
//	}


}
