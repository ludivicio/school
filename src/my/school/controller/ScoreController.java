package my.school.controller;

import java.text.ParseException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my.school.config.Constants;
import my.school.interceptor.ScoreInterceptor;
import my.school.kit.DateKit;
import my.school.kit.ParaKit;
import my.school.model.Admin;
import my.school.model.Class;
import my.school.model.Course;
import my.school.model.Grade;
import my.school.model.School;
import my.school.model.Score;
import my.school.model.Student;
import my.school.model.Teacher;
import my.school.model.Term;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

/**
 * ScoreController
 * 
 * 成绩管理
 * 
 */
@Before(ScoreInterceptor.class)
public class ScoreController extends Controller {

	public void index() {

		redirect("search.html");
	}

	/**
	 * 搜索
	 */
	public void search() {

		int page = ParaKit.paramToInt(getPara("p"), 1);

		if (page < 1) {
			page = 1;
		}

		School curSchool = null;
		Term curTerm = null;
		Class curClass = null;

		Admin admin = getSessionAttr("admin");
		setAttr("admin", admin);

		if (admin.getInt("rid") == 3) {
			int teacherId = admin.getInt("tid");

			Teacher teacher = Teacher.dao.findById(teacherId);
			curSchool = teacher.getSchool();
			curClass = teacher.getClazz();
			curTerm = Term.dao.getLastTerm();

		} else if (admin.getInt("rid") == 1) {
			int tid = ParaKit.paramToInt(getPara("tid"), -1);
			int sid = ParaKit.paramToInt(getPara("sid"), -1);
			int cid = ParaKit.paramToInt(getPara("cid"), -1);

			// 查询该学校的所有班级
			List<Class> classList = Class.dao.getClassesBySchoolId(sid);
			setAttr("classList", classList);

			curTerm = Term.dao.findById(tid);
			curSchool = School.dao.findById(sid);
			curClass = Class.dao.findById(cid);
		}

		if (curClass == null) {
			render("add.html");
			return;
		}

		// 查询该班级下的所有学生信息
		Page<Student> studentList = Student.dao.paginate(page, Constants.PAGE_SIZE,
				curClass.getInt("id"));
		setAttr("studentList", studentList);

		// 获取该学期下该班级的所有课程信息

		String end = curTerm.getStr("end");

		System.out.println("end: " + end);

		int gid = -1;

		try {

			int year = DateKit.getYear("yyyy年M月", end);
			int month = DateKit.getMonth("yyyy年M月", end);

			System.out.println("year: " + year + "  month: " + month);

			String startYear = curClass.getStr("year");
			gid = year - Integer.parseInt(startYear);

			System.out.println("grade: " + gid);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (gid < 1) {

			render("add.html");

			return;
		}

		// 这里使用阿拉伯数字的年级来代表年级的ID

		List<Course> courseList = Course.dao.getCoursesByGradeId(gid);
		setAttr("courseList", courseList);

		Grade grade = Grade.dao.findById(gid);
		setAttr("grade", grade);

		setAttr("curTerm", curTerm);
		setAttr("curSchool", curSchool);
		setAttr("curClass", curClass);

		render("add.html");

	}

	public void getClassBySchool() {

		int sid = ParaKit.paramToInt(getPara(0), -1);

		if (sid > 0) {

			List<Class> classList = Class.dao.getClassesBySchoolId(sid);

			Map<String, String> result = new HashMap<String, String>();

			for (Class clazz : classList) {
				result.put(String.valueOf(clazz.get("id")), clazz.getStr("name"));
			}

			renderJson("json", result);

			return;

		}

		renderJson("json", "");

	}

	/**
	 * 录入学生成绩
	 */
	public void save() {

		int sid = ParaKit.paramToInt(getPara("sid"), -1);
		int tid = ParaKit.paramToInt(getPara("tid"), -1);

		Enumeration<String> params = getParaNames();
		String paramName = null;
		String scoreId = null;
		while (params.hasMoreElements()) {
			paramName = params.nextElement();
			if (paramName.startsWith("s_" + sid + "_")) {
				scoreId = paramName.substring(paramName.lastIndexOf("_") + 1);

				String scoreStr = getPara(paramName);

				if (ParaKit.isEmpty(scoreStr)) {

					setAttr("status", "error");
					setAttr("errorMsg", "分数不能为空！");
					renderJson();
					return;
				}

				int s = -1;
				try {
					s = Integer.parseInt(scoreStr);
					if (s < 0 || s > 100) {
						setAttr("status", "error");
						setAttr("errorMsg", "分数填写错误！");
						renderJson();
						return;
					}
				} catch (NumberFormatException e) {
					setAttr("status", "error");
					setAttr("errorMsg", "分数填写错误！");
					renderJson();
					return;
				}

				Score score = new Score();
				score.set("tid", tid);
				score.set("sid", sid);
				score.set("cid", scoreId);
				score.set("score", s);
				score.save();

				System.out.println("score: " + score);

			}
		}

		setAttr("status", "success");
		renderJson();
	}

}
