package my.school.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my.school.config.Constants;
import my.school.interceptor.ClassInterceptor;
import my.school.kit.Cn2Spell;
import my.school.kit.ParaKit;
import my.school.model.Admin;
import my.school.model.Class;
import my.school.model.School;
import my.school.model.Teacher;
import my.school.validator.SaveClassValidator;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

/**
 * ClassController
 * 
 * 班级管理
 * 
 */
@Before(ClassInterceptor.class)
public class ClassController extends Controller {

	public void index() {

		// 判断当前是否是搜索的数据进行的分页
		// 如果是搜索的数据，则跳转至search方法处理
		if (!ParaKit.isEmpty(getPara("s"))) {

			search();

			return;
		}

		int page = ParaKit.paramToInt(getPara("p"), 1);

		if (page < 1) {
			page = 1;
		}

		// 读取所有的班级信息
		Page<Class> classList = Class.dao.paginate(page, Constants.PAGE_SIZE);

		setAttr("classList", classList);

		setAttr("searchName", "");
		setAttr("searchSid", "");
		setAttr("searchTid", "");
		setAttr("searchPage", Constants.NOT_SEARCH_PAGE);

		render("index.html");
	}

	@Before(ClassInterceptor.class)
	public void add() {

		render("add.html");
	}

	/**
	 * 搜索
	 */
	public void search() {

		if (ParaKit.isEmpty(getPara("s"))) {

			// 说明当前请求是搜索数据的post请求，并非搜索的分页请求
			// 在这里执行搜索操作，并将结果保存到缓存中

			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("name", getPara("name"));
			queryParams.put("tid", getPara("tid"));
			queryParams.put("sid", getPara("sid"));

			setSessionAttr(Constants.SEARCH_SESSION_KEY, queryParams);

		}

		int page = ParaKit.paramToInt(getPara("p"), 1);

		if (page < 1) {
			page = 1;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("from class where id > 0");

		HashMap<String, String> queryParams = getSessionAttr(Constants.SEARCH_SESSION_KEY);
		List<Object> params = new ArrayList<Object>();

		if (queryParams != null) {

			String name = queryParams.get("name");

			if (!ParaKit.isEmpty(name)) {
				sb.append(" and name like ?");
				params.add("%" + name + "%");
			}

			int sid = Integer.parseInt(queryParams.get("sid"));
			if (sid > -1) {
				sb.append(" and sid = ?");
				params.add(sid);
			}

			int tid = Integer.parseInt(queryParams.get("tid"));
			if (tid > -1) {
				sb.append(" and tid = ?");
				params.add(tid);
			}

			setAttr("searchName", name);
			setAttr("searchSid", sid);
			setAttr("searchTid", tid);
			setAttr("searchPage", Constants.SEARCH_PAGE);

			// 读取所有班主任的信息
			List<Teacher> teacherList = Teacher.dao.getHeadTeachers(sid);
			setAttr("teacherList", teacherList);

		}

		// 班级列表
		Page<Class> classList = Class.dao.paginate(page, Constants.PAGE_SIZE, "select *",
				sb.toString(), params.toArray());

		setAttr("classList", classList);

		render("index.html");

	}

	/**
	 * 添加/修改班级信息
	 */
	@Before(SaveClassValidator.class)
	public void save() {

		Class clazz = getModel(Class.class);

		if (clazz.getInt("sid") < 0 || clazz.getInt("tid") < 0) {
			setAttr("status", "failed");
			renderJson();
			return;
		}

		String year = clazz.getStr("year");
		String sid = String.valueOf(clazz.getInt("sid"));

		if("-1".equals(sid)) {
			setAttr("status", "failed");
			renderJson();
			return;
		}
		
		System.out.println("year: " + year);

		System.out.println("id: " + clazz.getInt("id"));

		School school = School.dao.findById(sid);
		
		if (clazz.getInt("id") == null) {

			// 首先找到该学校该年份所有的班级数量
			int count = Class.dao.getCount(year, sid);

			System.out.println("count: " + count);

			count += 1;

			String uuid = null;
			String name = null;

			// uuid 的格式为： 学校uuid + 年份的后两位 作为前缀，后面累加
			
			if (count < 10) {
				uuid = school.getStr("uuid") + year.substring(2) + "0" + count;
				name = year + "级0" + count + "班";
			} else {
				uuid = school.getStr("uuid") + year.substring(2) + count;
				name = year + "级" + count + "班";
			}

			System.out.println("name: " + name + "  uuid: " + uuid);

			clazz.set("year", year);
			clazz.set("name", name);
			clazz.set("uuid", uuid);

			boolean result = clazz.save();

			if (result) {

				// 读取教师信息，将其改为班主任
				Teacher teacher = Teacher.dao.findById(clazz.get("tid"));
				if (teacher != null) {
					teacher.set("rid", 3);
					teacher.update();
				} else {
					setAttr("status", "failed");
					renderJson();
					return;
				}

				// 在管理员表中创建账号
				Admin admin = new Admin();
				admin.set("account", teacher.getStr("uuid"));
				admin.set("password", Cn2Spell.getInstance().getSpelling(teacher.getStr("name")));
				admin.set("time", null);
				admin.set("tid", teacher.getInt("id"));
				admin.save();

				setAttr("status", "success");
				setAttr("action", "create");
				setAttr("name", name);
				setAttr("uuid", uuid);

			} else {
				setAttr("status", "failed");
			}

		} else {

			boolean result = clazz.update();
			if (result) {

				Teacher oldTeacher = Teacher.dao.getTeacherByClassId(clazz.getInt("id"));
				Teacher newTeacher = Teacher.dao.findById(clazz.getInt("tid"));

				if (oldTeacher.getInt("id") != newTeacher.getInt("id")) {
					oldTeacher.set("rid", 4);
					oldTeacher.update();

					newTeacher.set("rid", 3);
					newTeacher.update();

					Admin admin = Admin.dao.getAdminByTeacherId(oldTeacher.getInt("id"));
					if (admin != null) {
						admin.set("account", newTeacher.getStr("uuid"));
						admin.set("password",
								Cn2Spell.getInstance().getSpelling(newTeacher.getStr("name")));
						admin.set("time", null);
						admin.set("tid", newTeacher.getInt("id"));
						admin.update();
					} else {
						// 在管理员表中创建账号
						admin = new Admin();
						admin.set("account", newTeacher.getStr("uuid"));
						admin.set("password",
								Cn2Spell.getInstance().getSpelling(newTeacher.getStr("name")));
						admin.set("rid", 3);
						admin.set("time", null);
						admin.set("tid", newTeacher.getInt("id"));
						admin.save();
					}

				}

				setAttr("status", "success");
				setAttr("action", "update");

			} else {
				setAttr("status", "failed");
			}
		}

		renderJson();
	}

	/**
	 * 跳转编辑页面
	 * 
	 */
	public void edit() {
		int classId = getParaToInt(0);

		Class clazz = Class.dao.findById(classId);

		// 读取所有任课老师的信息
		List<Teacher> teacherList = Teacher.dao.getNormalTeachers(clazz.getInt("sid"));
		Teacher teacher = Teacher.dao.findById(clazz.get("tid"));
		teacherList.add(teacher);
		System.out.println("teacher: " + teacher.getStr("name"));
		for(Teacher t : teacherList) {
			System.out.println("t: " + t.getStr("name"));
		}
		
		setAttr("teacherList", teacherList);

		setAttr("class", clazz);
		render("add.html");
	}

	/**
	 * 采用AJAX方式，根据学校ID和角色ID获取教师信息
	 */
	public void getTeachers() {

		// 学校ID
		int sid = ParaKit.paramToInt(getPara("s"), -1);
		// 角色ID
		int rid = ParaKit.paramToInt(getPara("r"), -1);

		if (sid > -1) {

			List<Teacher> teachers = null;

			if (rid == 3) {
				teachers = Teacher.dao.getHeadTeachers(sid);
			} else if (rid == 4) {
				teachers = Teacher.dao.getNormalTeachers(sid);
			}

			if (teachers != null) {

				System.out.println("teachers.size = " + teachers.size());

				Map<String, String> result = new HashMap<String, String>();

				for (Teacher teacher : teachers) {
					result.put(String.valueOf(teacher.get("id")), teacher.getStr("name"));
				}

				renderJson("json", result);

				return;
			}

		}

		renderJson("json", "");

	}

	/**
	 * 删除科室信息
	 */
	public void delete() {
		int classId = ParaKit.paramToInt(getPara(0), -1);

		if (classId > -1) {
			if (Class.dao.deleteById(classId)) {
				renderJson("msg", "删除成功！");
			}
		} else {
			renderJson("msg", "删除失败！");
		}

	}

}
