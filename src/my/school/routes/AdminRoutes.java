package my.school.routes;

import my.school.controller.AssignController;
import my.school.controller.ClassController;
import my.school.controller.CourseController;
import my.school.controller.IndexController;
import my.school.controller.MyInfoController;
import my.school.controller.SchoolController;
import my.school.controller.ScoreController;
import my.school.controller.StudentController;
import my.school.controller.TeacherController;
import my.school.controller.TermController;

import com.jfinal.config.Routes;

public class AdminRoutes extends Routes {

	@Override
	public void config() {

		// 后台首页
		add("/admin", IndexController.class);

		// 个人信息
		add("/admin/myinfo", MyInfoController.class);

		// 学校管理
		add("/admin/school", SchoolController.class);

		// 班级管理
		add("/admin/class", ClassController.class);

		// 教师信息管理
		add("/admin/teacher", TeacherController.class);

		// 学生信息管理
		add("/admin/student", StudentController.class);

		// 学期管理
		add("/admin/term", TermController.class);

		// 课程管理
		add("/admin/course", CourseController.class);

		// 成绩管理
		add("/admin/score", ScoreController.class);

		// 学期课程分配
		add("/admin/assign", AssignController.class);

	}

}
