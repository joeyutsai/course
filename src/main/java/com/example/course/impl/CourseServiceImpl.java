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
			// check time and credits
			Set<String> studentListCode = stringToSet(idOp.get().getSelectedCode());
			String[] checkCourse = selectCourseCheck(listCode, studentListCode);
			if (checkCourse[1] == null) {
				Student student = idOp.get();

				student.setCredits(Integer.parseInt(checkCourse[0]));
				student.setSelectedCode(checkCourse[2]);
				System.out.println("selectCourseCode checkCourse[2]" + checkCourse[2]);

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
			String[] checkCourse = withdrawCourseCheck(listCode, studentListCode);

			if (checkCourse[1].isEmpty()) {
				student.setCredits(0);
				student.setSelectedCode("");
			} else {
				if (checkCourse[0] == null && checkCourse[1] == null) {
					System.out.println("ERROR. withdrawCourseCheck");
				} else {
					student.setCredits(Integer.parseInt(checkCourse[0]));
					student.setSelectedCode(checkCourse[1].substring(1, checkCourse[1].length() - 1));
				}
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

			for (String item : studentListCode) {
				Optional<Course> courseOp = courseDao.findById(item);
				if (courseOp.isPresent()) {
					selectedListCourse.add(courseOp.get());
				}
			}
			return new StudentRes(idOp.get(), selectedListCourse);
		}
		return null;
	}

	public String[] selectCourseCheck(Set<String> listCode, Set<String> studentListCode) {
		Set<String> forDBStudentListCode = new HashSet<>();
		List<Course> listCourse = new ArrayList<>();
		String[] message = { null, null, null }; // {credits, courseTimeCheckMessage ,stringStudentListCode}

//		List<Course> courseListInDB = courseDao.findAllByIdIn(listCode);
//		for (Course item : courseListInDB) {
//			totCredits += item.getCredits();
//			listCourse.add(item);
//		}

		// 將 studentListCode的 listCode設定好。
		for (String studentListCodeItem : studentListCode) {
			forDBStudentListCode.add(studentListCodeItem);
		}
		// check listCode in DB, if in DB add to forDBStudentListCode
		// 優化：courseDao只進資料庫一次，不要用迴圈多次進入資料庫
		for (String listCodeItem : listCode) {
			Optional<Course> courseOp = courseDao.findById(listCodeItem);
			if (courseOp.isPresent()) {
				forDBStudentListCode.add(courseOp.get().getCode());
			}
		}

		// 從 courseDB中取出course (forDBStudentListCode)
		for (String forDBStudentListCodeItem : forDBStudentListCode) {
			Optional<Course> courseOp = courseDao.findById(forDBStudentListCodeItem);
			if (courseOp.isPresent()) {
				listCourse.add(courseOp.get());
			}
		}

		String stringCodeSet = forDBStudentListCode.toString();
		message[2] = stringCodeSet.substring(1, stringCodeSet.length() - 1);
//		System.out.println("selectCourseCheck message[2]: " + message[2]);
//		System.out.println("selectCourseCheck listCourse.size(): " + listCourse.size());

		// credits limit
		int totCredits = 0;
		for (Course courseItem : listCourse) {
			totCredits += courseItem.getCredits();
		}

		if (totCredits > 10) {
			message[1] = "ERROR-credits";
		} else {
			message[0] = String.valueOf(totCredits);

			// course time (first check: weektime, second check: startTime and endTime)
			for (Course item : listCourse) {
				for (int i = 0; i < listCourse.size(); i++) {
					if (item.getWeekday() == listCourse.get(i).getWeekday()) {

						if (!item.getCode().equalsIgnoreCase(listCourse.get(i).getCode())) {

							if (listCourse.get(i).getStartTime() == item.getStartTime()
									&& item.getEndTime() == listCourse.get(i).getEndTime()) {
								message[1] = "ERROR-courseTime-sameTime";
							} else if ((listCourse.get(i).getStartTime() < item.getStartTime()
									&& item.getStartTime() < listCourse.get(i).getEndTime())
									|| (listCourse.get(i).getStartTime() < item.getEndTime()
											&& item.getEndTime() < listCourse.get(i).getEndTime())) {
								message[1] = "ERROR-courseTime-inTheRangeTime";
							}
						}
					}
				}
			}
		}
//		System.out.println("message[1]: " + message[1]);
		return message;
	}

	public String[] withdrawCourseCheck(Set<String> listCode, Set<String> studentListCode) {
		String[] message = { null, null };
		// 確認輸入的課程代碼是否存在於 courseDB中，若存在則將其課程代碼放入 listCodeCheck
		Set<String> listCodeCheck = new HashSet<>();
		for (String listCodeItem : listCode) {
			Optional<Course> courseOp = courseDao.findById(listCodeItem);
			if (courseOp.isPresent()) {
				listCodeCheck.add(listCodeItem);
			}
		}

		// listCodeCheck與 studentListCode做比對。
		// 比對後，將相同的課程代碼放入 sameListCode
		Set<String> sameListCode = new HashSet<>();
		for (String item : listCodeCheck) {
			for (String studentItem : studentListCode) {
				if (item.equalsIgnoreCase(studentItem)) {
					sameListCode.add(item);
				}
			}
		}

		// 去除 studentListCode中 sameListCode的所有項目。
		for (String item : sameListCode) {
			studentListCode.remove(item);
		}

		// studentListCode 去比對courseDB中的資料並取出，再設定 message(selectedCode & credits)
		Set<String> newListCode = new HashSet<>();
		int totCredits = 0;

		if (!sameListCode.isEmpty() && studentListCode.isEmpty()) {
			message[0] = String.valueOf(totCredits);
			message[1] = "";
		} else {
			for (String listStudentCodeItem : studentListCode) {
				Optional<Course> courseOp = courseDao.findById(listStudentCodeItem);
				if (courseOp.isPresent()) {
					newListCode.add(listStudentCodeItem);
					totCredits += courseOp.get().getCredits();
				}
				message[0] = String.valueOf(totCredits);
				message[1] = newListCode.toString();
			}
		}
		return message;
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
