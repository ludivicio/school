package my.school.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my.school.config.Constants;
import my.school.interceptor.ScoreInterceptor;
import my.school.kit.DateKit;
import my.school.kit.ParaKit;
import my.school.model.Class;
import my.school.model.Course;
import my.school.model.Grade;
import my.school.model.Student;
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

		render("index.html");
	}

	public void add() {

		render("add.html");
	}

	/**
	 * 搜索
	 */
	public void search() {

		int page = ParaKit.paramToInt(getPara("p"), 1);

		if (page < 1) {
			page = 1;
		}

		int tid = ParaKit.paramToInt(getPara("tid"), -1);
		int sid = ParaKit.paramToInt(getPara("sid"), -1);
		int cid = ParaKit.paramToInt(getPara("cid"), -1);

		// 查询该学校的所有班级
		List<Class> classList = Class.dao.getClassesBySchoolId(sid);
		setAttr("classList", classList);

		// 查询该班级下的所有学生信息
		Page<Student> studentList = Student.dao.paginate(page, Constants.PAGE_SIZE, cid);
		setAttr("studentList", studentList);

		// 获取该学期下该班级的所有课程信息
		Term term = Term.dao.findById(tid);
		String end = term.getStr("end");

		System.out.println("end: " + end);

		int gid = -1;

		try {

			int year = DateKit.getYear("yyyy年M月", end);
			int month = DateKit.getMonth("yyyy年M月", end);

			System.out.println("year: " + year + "  month: " + month);

			Class clazz = Class.dao.findById(cid);
			String startYear = clazz.getStr("year");
			gid = year - Integer.parseInt(startYear);

			System.out.println("grade: " + gid);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
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
		
		setAttr("curTid", tid);
		setAttr("curSid", sid);
		setAttr("curCid", cid);

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
	 * 添加/修改科室信息处理方法
	 */
	public void save() {

		redirect("index.html");
	}

	/**
	 * 跳转编辑页面
	 * 
	 */
	public void edit() {

		render("add.html");
	}

	/**
	 * 删除科室信息
	 */
	public void delete() {

		redirect("index.html");

	}

}
