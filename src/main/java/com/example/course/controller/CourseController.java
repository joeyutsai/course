package com.example.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.course.constants.CourseRtnCode;
import com.example.course.entity.Course;
import com.example.course.entity.Student;
import com.example.course.ifs.CourseService;
import com.example.course.vo.CourseReq;
import com.example.course.vo.CourseRes;
import com.example.course.vo.CourseSelectedReq;
import com.example.course.vo.StudentReq;
import com.example.course.vo.StudentRes;

@RestController
public class CourseController {
	@Autowired
	private CourseService courseService;

	@PostMapping(value = "/api/add_course")
	public CourseRes addCourse(@RequestBody CourseReq req) {
		CourseRes checkParam = checkCourseParam(req);
		if (checkParam != null) {
			return checkParam;
		}

		Course newCourse = courseService.addCourse(req.getCode(), req.getName(), req.getWeekday(), req.getStartTime(),
				req.getEndTime(), req.getCredits());

		if (newCourse == null) {
			return new CourseRes(CourseRtnCode.COURSE_DUPLICATE.getMessage());
		}

		return new CourseRes(newCourse, CourseRtnCode.SUCCESSFUL.getMessage());
	}

	@PostMapping(value = "/api/update_course")
	public CourseRes updateCourse(@RequestBody CourseReq req) {
		CourseRes checkParam = checkCourseParam(req);
		if (checkParam != null) {
			return checkParam;
		}

		Course updateCourse = courseService.updateCourse(req.getCode(), req.getName(), req.getWeekday(),
				req.getStartTime(), req.getEndTime(), req.getCredits());
		if (updateCourse != null) {
			return new CourseRes(updateCourse, CourseRtnCode.SUCCESSFUL.getMessage());
		}
		
		return new CourseRes(CourseRtnCode.COURSE_UPDATE_FAILURE.getMessage());
	}

	@PostMapping(value = "/api/add_student")
	public StudentRes addStudent(@RequestBody StudentReq req) {
		StudentRes checkParam = checkStudentParam(req);
		if (checkParam != null) {
			return checkParam;
		}

		Student newStudent = courseService.addStudent(req.getId(), req.getName());
		if (newStudent == null) {
			return new StudentRes(CourseRtnCode.STUDENT_DUPLICATE.getMessage());
		}
		return new StudentRes(newStudent, CourseRtnCode.SUCCESSFUL.getMessage());
	}

	@PostMapping(value = "/api/update_student")
	public StudentRes updateCourse(@RequestBody StudentReq req) {
		StudentRes checkParam = checkStudentParam(req);
		if (checkParam != null) {
			return checkParam;
		}

		Student updateStudent = courseService.updateStudent(req.getId(), req.getName());
		if (updateStudent != null) {
			return new StudentRes(updateStudent, CourseRtnCode.SUCCESSFUL.getMessage());
		}
		
		return new StudentRes(CourseRtnCode.STUDENT_UPDATE_FAILURE.getMessage());
	}
	
	@PostMapping(value = "/api/select_course")
	public StudentRes selectCourse(@RequestBody CourseSelectedReq req) {
		StudentRes checkParam = checkSelectedCourseParam(req);
		if (checkParam != null) {
			return checkParam;
		}

		Student newSelectedCourse = courseService.selectCourseCode(req.getId(), req.getListCode());
		if (newSelectedCourse == null) {
			return new StudentRes(CourseRtnCode.COURSE_SELECTED_FAILURE.getMessage());
		}
		return new StudentRes(newSelectedCourse, CourseRtnCode.SUCCESSFUL.getMessage());
	}

	@PostMapping(value = "/api/withdraw_course")
	public StudentRes withdrawCourse(@RequestBody CourseSelectedReq req) {
		StudentRes checkParam = checkSelectedCourseParam(req);
		if (checkParam != null) {
			return checkParam;
		}

		Student newSelectedCourse = courseService.withdrawCourseCode(req.getId(), req.getListCode());
		if (newSelectedCourse == null) {
			return new StudentRes(CourseRtnCode.COURSE_WITHDRAW_FAILURE.getMessage());
		}
		return new StudentRes(newSelectedCourse, CourseRtnCode.SUCCESSFUL.getMessage());
	}

	@PostMapping(value = "/api/student_course_info")
	public StudentRes studentCourseInfo(@RequestBody StudentReq req) {
		if (!StringUtils.hasText(req.getId())) {
			return new StudentRes(CourseRtnCode.ID_REQUIRED.getMessage());
		}
		StudentRes res = courseService.studentCourseInfo(req.getId());
		res.setMessage(CourseRtnCode.SUCCESSFUL.getMessage());
		return res;

	}

	private CourseRes checkCourseParam(CourseReq req) {
		if (!StringUtils.hasText(req.getCode()) || req.getCode() == null) {
			return new CourseRes(CourseRtnCode.CODE_REQUIRED.getMessage());
		} else if (!StringUtils.hasText(req.getName()) || req.getName() == null) {
			return new CourseRes(CourseRtnCode.NAME_REQUIRED.getMessage());
		} else if (req.getWeekday() < 1 || req.getWeekday() > 5) {
			return new CourseRes(CourseRtnCode.WEEKDAY_FAILURE.getMessage());
		} else if (req.getStartTime() > req.getEndTime()) {
			return new CourseRes(CourseRtnCode.COURSETIME_FAILURE.getMessage());
		}
		return null;
	}

	private StudentRes checkStudentParam(StudentReq req) {
		if (!StringUtils.hasText(req.getId()) || req.getId() == null) {
			return new StudentRes(CourseRtnCode.ID_REQUIRED.getMessage());
		} else if (!StringUtils.hasText(req.getName()) || req.getName() == null) {
			return new StudentRes(CourseRtnCode.NAME_REQUIRED.getMessage());
		}
		return null;
	}

	private StudentRes checkSelectedCourseParam(CourseSelectedReq req) {
		if (!StringUtils.hasText(req.getId()) || req.getId() == null) {
			return new StudentRes(CourseRtnCode.ID_REQUIRED.getMessage());
		} else if (!StringUtils.hasText(req.getListCode().toString()) || req.getListCode().toString() == null) {
			return new StudentRes(CourseRtnCode.LISTCODE_REQUIRED.getMessage());
		}
		return null;
	}

}
