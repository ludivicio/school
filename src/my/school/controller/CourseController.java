package my.school.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my.school.config.Constants;
import my.school.kit.ParaKit;
import my.school.kit.UUID;
import my.school.model.Course;
import my.school.validator.SaveCourseValidator;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

/**
 * CourseController
 * 
 * 课程管理
 * 
 */
public class CourseController extends Controller {

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
		// 读取所有的科室信息。
		Page<Course> courseList = Course.dao.paginate(page, Constants.PAGE_SIZE);
		setAttr("courseList", courseList);
		setAttr("searchName", "");
		setAttr("searchUuid", "");
		setAttr("searchPage", Constants.NOT_SEARCH_PAGE);
		render("index.html");
	}

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
			queryParams.put("uuid", getPara("uuid"));
			setSessionAttr(Constants.SEARCH_SESSION_KEY, queryParams);

		}

		int page = ParaKit.paramToInt(getPara("p"), 1);

		if (page < 1) {
			page = 1;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("from course where id > 0");

		HashMap<String, String> queryParams = getSessionAttr(Constants.SEARCH_SESSION_KEY);
		List<Object> params = new ArrayList<Object>();

		if (queryParams != null) {

			String name = queryParams.get("name");

			if (!ParaKit.isEmpty(name)) {
				sb.append(" and name like ?");
				params.add("%" + name + "%");
			}

			String uuid = queryParams.get("uuid");

			if (!ParaKit.isEmpty(uuid)) {
				sb.append(" and uuid like ?");
				params.add("%" + uuid + "%");
			}
			setAttr("searchName", name);
			setAttr("searchUuid", uuid);
			setAttr("searchPage", Constants.SEARCH_PAGE);

		}

		// 课程列表
		Page<Course> courseList = Course.dao.paginate(page, Constants.PAGE_SIZE, "select *",
				sb.toString(), params.toArray());

		setAttr("courseList", courseList);

		render("index.html");

	}

	/**
	 * 添加/修改课程信息处理方法
	 */
	@Before(SaveCourseValidator.class)
	public void save() {
		Course course = getModel(Course.class);
		// 排序位置
		if (course.get("sort") == null || course.get("sort").equals("")) {
			course.set("sort", 1);
		}
		if (course.get("id") == null) {
			course.set("uuid", UUID.randomUUID());
			course.save();
		} else {
			course.update();
		}
		redirect("index.html");
	}

	/**
	 * 跳转编辑页面
	 * 
	 */
	public void edit() {
		int courseId = getParaToInt(0);
		setAttr("course", Course.dao.findById(courseId));
		render("add.html");
	}

	/**
	 * 删除科室信息
	 */
	public void delete() {
		int courseId = ParaKit.paramToInt(getPara(0), -1);
		if (courseId > -1) {
			if (Course.dao.deleteById(courseId)) {
				renderJson("msg", "删除成功！");
			}
		} else {
			renderJson("msg", "删除失败！");
		}

	}

}
