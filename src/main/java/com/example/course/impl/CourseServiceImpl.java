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
//						System.out.println("ERROR. String item : " + "-" + item + "-");
					}

				}
				String stringCodeSet = codeSet.toString();
//				System.out.println("before substring -> stringCodeSet: " + stringCodeSet);
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

	public Student idAndListCodeCheck(String id, Set<String> listCode) {
		Optional<Student> idOp = studentDao.findById(id);
		if (idOp.isPresent()) {
			Set<String> codeSet = new HashSet<>();
			for (String item : listCode) {
				codeSet.add(item);
			}

			Student student = idOp.get();
			String[] codeArray = student.getSelectedCode().split(",");
			for (String item : codeArray) {
				codeSet.add(item.trim());
			}

			String newCodeSet = codeSet.toString().substring(1, codeSet.toString().length() - 1);
			student.setSelectedCode(newCodeSet);
			return student;
		}
		return null;
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
		System.out.println("IMPL withdrawCourseCheck sameListCode: " + sameListCode.toString());

		// 去除 studentListCode中 sameListCode的所有項目。
		for (String item : sameListCode) {
			studentListCode.remove(item);
		}
		System.out.println("IMPL withdrawCourseCheck studentListCode(removed): " + studentListCode.toString());

		// studentListCode 去比對courseDB中的資料並取出，再設定 message(selectedCode & credits)
		Set<String> newListCode = new HashSet<>();
		int totCredits = 0;

		if (!sameListCode.isEmpty() && studentListCode.isEmpty()) {
			message[0] = String.valueOf(totCredits); // for student DB credits
			message[1] = ""; // for student DB selectedCode
		} else {
			for (String listStudentCodeItem : studentListCode) {
				Optional<Course> courseOp = courseDao.findById(listStudentCodeItem);
				if (courseOp.isPresent()) {
					newListCode.add(listStudentCodeItem);
					totCredits += courseOp.get().getCredits();
				}
				message[0] = String.valueOf(totCredits); // for student DB credits
				message[1] = newListCode.toString(); // for student DB selectedCode

			}
		}

		System.out.println("IMPL withdrawCourseCheck TotCredits: " + message[0]);
		System.out.println("IMPL withdrawCourseCheck StudentListCode: " + message[1]);

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
