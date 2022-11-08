package com.example.course.impl;

import java.util.ArrayList;
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

@Service
public class CourseServiceImpl implements CourseService {
	@Autowired
	private CourseDao courseDao;

	@Autowired
	private StudentDao studentDao;

	@Override
	public Course addCoures(String code, String name, int weekday, int startTime, int endTime, int credits) {

		if (courseDao.existsById(code)) {
			return null;
		}

		Course newCourse = new Course(code, name, weekday, startTime, endTime, credits);
		return courseDao.save(newCourse);
	}

	@Override
	public Student addStudent(String id, String name) {
		if (studentDao.existsById(id)) {
			return null;
		}

		Student newStudent = new Student(id, name);
		return studentDao.save(newStudent);
	}

	@Override
	public Student selectCourseCode(String id, Set<String> listCode) {
		Optional<Student> idOp = studentDao.findById(id);
		if (idOp.isPresent()) {
			Set<String> codeSet = new HashSet<>();

			for (String item : listCode) {
//				System.out.println("codeSet String item : " + "-" + item + "-");
				codeSet.add(item);
			}

			// check time and credits
//			System.out.println("before check: Credits: " + selectCourseCheck(codeSet)[0] + " Message: "
//					+ selectCourseCheck(codeSet)[1]);
			String[] checkCourse = selectCourseCheck(codeSet);
			if (checkCourse[1] == null) {
				Student student = idOp.get();
//				System.out.println("idOp.get().getSelectedCode(): " + idOp.get().getSelectedCode());

				student.setCredits(Integer.parseInt(checkCourse[0]));

//				System.out.println("student.getSelectedCode(): " + student.getSelectedCode());
				String code = student.getSelectedCode();
//				System.out.println("code = student.getSelectedCode(): " + code);
				String[] codeArray = code.split(",");
				for (String item : codeArray) {

					if (!(item.trim() == " ")) { // i can't use StringUtils.hasText() ???why???
//						System.out.println("String item : " + "-" + item + "-");
						codeSet.add(item.trim());
					} else if (item.trim() == " ") {
						System.out.println("ERROR. String item : " + "-" + item + "-");
					}

				}
				String stringCodeSet = codeSet.toString();
				System.out.println("before substring -> stringCodeSet: " + stringCodeSet);
				String newCodeSet = stringCodeSet.substring(1, stringCodeSet.length() - 1);
				student.setSelectedCode(newCodeSet);

//				System.out.println("after substring -> newCodeSet: " + newCodeSet);

				return studentDao.save(student);
			}

		}
		System.out.println("ERROR. Without the id: " + id);

		return null;
	}
	
	@Override
	public Student withdrawCourseCode(String id, Set<String> listCode) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] selectCourseCheck(Set<String> listCode) {
		List<Course> listCourse = new ArrayList<>();
		String[] message = { null, null };

		// credits limit
		int totCredits = 0;
		for (String listCodeItem : listCode) {
			Optional<Course> courseOp = courseDao.findById(listCodeItem);
			if (courseOp.isPresent()) {
				listCourse.add(courseOp.get());
				totCredits += courseOp.get().getCredits();
//				System.out.println(
//						"CourseCode: " + courseOp.get().getCode() + " Credits: " + courseOp.get().getCredits());

			}
		}

		System.out.println("TotCredits: " + totCredits);

		if (totCredits > 10) {
			message[1] = "ERROR-credits";
			System.out.println("ERROR. LimitCredits: 10    SelectedTotCredits: " + totCredits);
		} else {
			message[0] = String.valueOf(totCredits);
			System.out.println("message[0]: " + message[0]);

			// course time (first check: weektime, second check: startTime and endTime)
			for (Course item : listCourse) {
				for (int i = 0; i < listCourse.size(); i++) {
					if (item.getWeekday() == listCourse.get(i).getWeekday()) {
						boolean checkCourseTime;

						if (!item.getCode().equalsIgnoreCase(listCourse.get(i).getCode())) {
							if (listCourse.get(i).getStartTime() == item.getStartTime()
									&& item.getEndTime() == listCourse.get(i).getEndTime()) {
								checkCourseTime = true;
								message[1] = "ERROR-courseTime-sameTime";
								System.out.println("message[1]: " + message[1]);

							} else if ((listCourse.get(i).getStartTime() < item.getStartTime()
									&& item.getStartTime() < listCourse.get(i).getEndTime())
									|| (listCourse.get(i).getStartTime() < item.getEndTime()
											&& item.getEndTime() < listCourse.get(i).getEndTime())) {
								checkCourseTime = true;
								message[1] = "ERROR-courseTime-inTheRangeTime";
								System.out.println("message[1]: " + message[1]);

							}
						}

					}
				}

			}
		}

		return message;
	}

	public String selectCourseCheck2(Set<String> listCode) {
		List<Course> listCourse = new ArrayList<>();
		String message = null;

		// credits limit
		int totCredits = 0;
		for (String listCodeItem : listCode) {
			Optional<Course> courseOp = courseDao.findById(listCodeItem);
			if (courseOp.isPresent()) {
				listCourse.add(courseOp.get());
				totCredits += courseOp.get().getCredits();
			}
		}

		System.out.println("TotCredits: " + totCredits);

		if (totCredits > 10) {
			message = "ERROR-credits";
//			message[1] = "ERROR-credits";
//			System.out.println("ERROR. Limit: 10 credits   TotCredits: " + totCredits);
		} else {
			// course time (first check: weektime, second check: startTime and endTime)
			for (Course item : listCourse) {
				for (int i = 0; i < listCourse.size(); i++) {
					if (item.getWeekday() == listCourse.get(i).getWeekday()) {
						boolean checkCourseTime;

						if (!item.getCode().equalsIgnoreCase(listCourse.get(i).getCode())) {
							if (listCourse.get(i).getStartTime() == item.getStartTime()
									&& item.getEndTime() == listCourse.get(i).getEndTime()) {
								checkCourseTime = true;
								message = "ERROR-courseTime-sameTime";
//								message[1] = "ERROR-courseTime-sameTime";

							} else if ((listCourse.get(i).getStartTime() < item.getStartTime()
									&& item.getStartTime() < listCourse.get(i).getEndTime())
									|| (listCourse.get(i).getStartTime() < item.getEndTime()
											&& item.getEndTime() < listCourse.get(i).getEndTime())) {
								checkCourseTime = true;
								message = "ERROR-courseTime-inTheRangeTime";
//								message[1] = "ERROR-courseTime-inTheRangeTime";

							}
						}

					}
				}

			}
		}

		return message;
	}

	public Student selectCourseCode2(String id, Set<String> listCode) {
		Optional<Student> idOp = studentDao.findById(id);
		if (idOp.isPresent()) {
			Set<String> codeSet = new HashSet<>();

			for (String item : listCode) {
				codeSet.add(item);
			}

			// check time and credits
			System.out.println("before check: " + selectCourseCheck(codeSet));

			if (selectCourseCheck(codeSet) == null) {
				Student student = idOp.get();

				String code = student.getSelectedCode();
				String[] codeArray = code.split(",");
				for (String item : codeArray) {
					String removeSpaceItem = item.trim();
					codeSet.add(removeSpaceItem);
				}
				String stringCodeSet = codeSet.toString();
				String newCodeSet = stringCodeSet.substring(1, stringCodeSet.length() - 1);
				student.setSelectedCode(newCodeSet);

				System.out.println(newCodeSet);

				studentDao.save(student);
			}

		}
		return null;
	}



}
