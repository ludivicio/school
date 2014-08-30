package my.school.controller;

import java.util.List;

import my.school.kit.ParaKit;
import my.school.model.Assign;
import my.school.model.Course;
import my.school.model.Grade;

import com.jfinal.core.Controller;

/**
 * AssignController
 * 
 * 年级课程分配
 * 
 */
public class AssignController extends Controller {

	public void index() {

		List<Grade> gradeList = Grade.dao.getGrades();

		List<Course> courseList = Course.dao.getCourses();

		setAttr("gradeList", gradeList);

		setAttr("courseList", courseList);

		render("index.html");
	}

	/**
	 * 年级排课保存处理
	 */
	public void save() {

		System.out.println("gid:" + getPara("gid"));
		System.out.println("course: " + getPara("course"));

		String gid = getPara("gid");

		String courses = getPara("course");

		if (ParaKit.isEmpty(courses)) {
			renderJson("status", "error");
			return;
		}

		if (courses.endsWith(",")) {
			courses = courses.substring(0, courses.length() - 1);
		}

		String[] cids = courses.split(",");
		System.out.println("cids: " + cids.toString());

		Grade grade = Grade.dao.findById(gid);
		grade.delCourses();

		Assign assign = new Assign();
		for (String cid : cids) {
			assign.set("gid", gid);
			assign.set("cid", cid);
		}

		setAttr("status", "success");

		renderJson();

	}

}
