package com.example.course.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course.entity.Course;
import com.example.course.entity.Student;
import com.example.course.ifs.CourseService;
import com.example.course.repository.CourseDao;
import com.example.course.repository.StudentDao;
import com.example.course.vo.CourseCheck;
import com.example.course.vo.StudentRes;

@Service
public class CourseServiceImpl implements CourseService {
	@Autowired
	private CourseDao courseDao;

	@Autowired
	private StudentDao studentDao;

	@Override
	public Course addCourse(String code, String name, int weekday, int startTime, int endTime, int credits) {

		if (courseDao.existsById(code)) {
			return null;
		}

		Course newCourse = new Course(code, name, weekday, startTime, endTime, credits);
		return courseDao.save(newCourse);
	}

	@Override
	public Course updateCourse(String code, String name, int weekday, int startTime, int endTime, int credits) {
		Optional<Course> courseOp = courseDao.findById(code);
		if (!courseOp.isPresent()) {
			return null;
		}
		Course updateCourse = new Course(code, name, weekday, startTime, endTime, credits);
		return courseDao.save(updateCourse);
	}

	@Override
	public Student addStudent(String id, String name) {
		if (studentDao.existsById(id)) {
			return null;
		}

		Student newStudent = new Student(id, name);
		newStudent.setSelectedCode("");
		return studentDao.save(newStudent);
	}

	@Override
	public Student updateStudent(String id, String name) {
		Optional<Student> studentOp = studentDao.findById(id);
		if (!studentOp.isPresent()) {
			return null;
		}
		Student updateStudent = studentOp.get();
		updateStudent.setName(name);
		return studentDao.save(updateStudent);
	}

	@Override
	public Student selectCourseCode(String id, Set<String> listCode) {
		Optional<Student> idOp = studentDao.findById(id);
		if (idOp.isPresent()) {
			Set<String> studentListCode = stringToSet(idOp.get().getSelectedCode());
			CourseCheck courseCheck = selectCourseCheck(listCode, studentListCode);

			if (courseCheck.getMessage() == null) {
				Student student = idOp.get();
				student.setCredits(courseCheck.getCredits());
				student.setSelectedCode(courseCheck.getStudentListCodeForDB());

				return studentDao.save(student);
			}
		}
		return null;
	}

	@Override
	public Student withdrawCourseCode(String id, Set<String> listCode) {

		Optional<Student> idOp = studentDao.findById(id);
		if (idOp.isPresent()) {
			Student student = idOp.get();
			Set<String> studentListCode = stringToSet(idOp.get().getSelectedCode());
			CourseCheck checkCourse = withdrawCourseCheck(listCode, studentListCode);

			if (checkCourse.isCheckResult() == true) {
				student.setCredits(checkCourse.getCredits());
				student.setSelectedCode(checkCourse.getStudentListCodeForDB());
			}
			return studentDao.save(student);
		}

		return null;
	}

	@Override
	public StudentRes studentCourseInfo(String id) {
		Set<Course> selectedListCourse = new HashSet<>();
		Optional<Student> idOp = studentDao.findById(id);
		if (idOp.isPresent()) {
			Student student = idOp.get();
			Set<String> studentListCode = stringToSet(student.getSelectedCode());

			List<Course> courseFromDB = courseDao.findAllById(studentListCode);
			if (!courseFromDB.isEmpty()) {
				for (Course item : courseFromDB) {
					selectedListCourse.add(item);
				}
			}
			return new StudentRes(idOp.get(), selectedListCourse);
		}
		return null;
	}

	public CourseCheck selectCourseCheck(Set<String> listCode, Set<String> studentListCode) {
		CourseCheck courseCheck = new CourseCheck();

		// combine studentListCode to listCode
		for (String item : studentListCode) {
			listCode.add(item);
		}

		// CHECK listCode in DB, and get course from DB
		List<Course> listCourse = courseDao.findAllById(listCode);

		// SETTING courseCheck studentListCodeForDB --> get list of code from listCourse
		Set<String> studentListCodeForDB = new HashSet<>();
		for (Course item : listCourse) {
			studentListCodeForDB.add(item.getCode());
		}
		// SETTING courseCheck studentListCodeForDB --> Set<String>studentListCodeForDB
		// to string, and remove “[” & “]” by substring()
		String stringListCodeForDB = studentListCodeForDB.toString();
		courseCheck.setStudentListCodeForDB(stringListCodeForDB.substring(1, stringListCodeForDB.length() - 1));

		// CHECK credits limit and SETTING courseCheck credits or message
		int totCredits = 0;
		for (Course courseItem : listCourse) { // get all of the credits from listCourse
			totCredits += courseItem.getCredits();
		}
		if (totCredits > 10) {
			courseCheck.setMessage("ERROR-credits-larger than 10");
		} else {
			courseCheck.setCredits(totCredits);

			// CHECK course time and SETTING courseCheck message
			for (Course item : listCourse) {
				for (int i = 0; i < listCourse.size(); i++) {
					if (item.getWeekday() == listCourse.get(i).getWeekday()) { // check: weekday

						if (!item.getCode().equalsIgnoreCase(listCourse.get(i).getCode())) { // ignore the same course
																								// comparison

							if (listCourse.get(i).getStartTime() == item.getStartTime()
									&& item.getEndTime() == listCourse.get(i).getEndTime()) { // check: startTime and
																								// endTime --> the same
																								// time course
								courseCheck.setMessage("ERROR-courseTime-sameTime");
							} else if ((listCourse.get(i).getStartTime() < item.getStartTime()
									&& item.getStartTime() < listCourse.get(i).getEndTime())
									|| (listCourse.get(i).getStartTime() < item.getEndTime()
											&& item.getEndTime() < listCourse.get(i).getEndTime())) { // check:
																										// startTime and
																										// endTime -->
																										// the course is
																										// in the range
																										// of time
								courseCheck.setMessage("ERROR-courseTime-inTheRangeTime");
							}
						}
					}
				}
			}
		}
		return courseCheck;
	}

	public CourseCheck withdrawCourseCheck(Set<String> listCode, Set<String> studentListCode) {
		CourseCheck courseCheck = new CourseCheck();

		// compare listCode and studentListCode, put same code into sameListCode
		Set<String> sameListCode = new HashSet<>();
		for (String item : listCode) {
			if (studentListCode.contains(item)) {
				sameListCode.add(item);
			}
		}

		// remove code
		for (String item : sameListCode) {
			studentListCode.remove(item);
		}

		// get course from DB by studentListCode
		List<Course> courseFromDB = courseDao.findAllById(studentListCode);
		Set<String> studentListCodeForDB = new HashSet<>();
		int totCredits = 0;

		for (Course item : courseFromDB) {
			studentListCodeForDB.add(item.getCode());
			totCredits += item.getCredits();
		}
		
		// SETTING courseCheck
		courseCheck.setCheckResult(true);
		courseCheck.setCredits(totCredits);
		String stringListCodeForDB = studentListCodeForDB.toString();
		courseCheck.setStudentListCodeForDB(stringListCodeForDB.substring(1, stringListCodeForDB.length() - 1));

		return courseCheck;
	}

	public Set<String> stringToSet(String stringListCode) {
		Set<String> codeSet = new HashSet<>();
		String[] codeArray = stringListCode.split(",");
		for (String item : codeArray) {
			codeSet.add(item.trim());
		}
		return codeSet;
	}

}
