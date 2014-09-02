package my.school.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my.school.config.Constants;
import my.school.interceptor.TeacherInterceptor;
import my.school.kit.DateKit;
import my.school.kit.ParaKit;
import my.school.kit.UUID;
import my.school.kit.UploadKit;
import my.school.model.Teacher;
import my.school.validator.SaveTeacherValidator;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

/**
 * TeacherController
 * 
 * 教师管理
 * 
 */
@Before(TeacherInterceptor.class)
public class TeacherController extends Controller {

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
		// 读取所有的教师
		Page<Teacher> teacherList = Teacher.dao.paginate(page, Constants.PAGE_SIZE);
		setAttr("teacherList", teacherList);
		
		setAttr("searchUuid", "");
		setAttr("searchName", "");
		setAttr("searchSex", -1);
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
			queryParams.put("uuid", getPara("uuid"));
			queryParams.put("name", getPara("name"));
			queryParams.put("sex", getPara("sex"));

			setSessionAttr(Constants.SEARCH_SESSION_KEY, queryParams);

		}

		int page = ParaKit.paramToInt(getPara("p"), 1);

		if (page < 1) {
			page = 1;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("from teacher where id > 0");

		HashMap<String, String> queryParams = getSessionAttr(Constants.SEARCH_SESSION_KEY);
		List<Object> params = new ArrayList<Object>();

		if (queryParams != null) {

			String uuid = queryParams.get("uuid");

			if (!ParaKit.isEmpty(uuid)) {
				sb.append(" and uuid like ?");
				params.add("%" + uuid + "%");
			}

			String name = queryParams.get("name");

			if (!ParaKit.isEmpty(name)) {
				sb.append(" and name like ?");
				params.add("%" + name + "%");
			}

			int sex = Integer.valueOf(queryParams.get("sex").toString());

			if (sex > -1) {
				sb.append(" and sex like ?");
				params.add("%" + sex + "%");
			}

			setAttr("searchUuid", uuid);
			setAttr("searchName", name);
			setAttr("searchSex", sex);

			setAttr("searchPage", Constants.SEARCH_PAGE);

		}

		// 教师列表
		Page<Teacher> teacherList = Teacher.dao.paginate(page, Constants.PAGE_SIZE, "select *",
				sb.toString(), params.toArray());

		setAttr("teacherList", teacherList);

		render("index.html");

	}

	/**
	 * 添加/修改教师信息处理方法
	 */
	@Before(SaveTeacherValidator.class)
	public void save() {

		UploadFile file = getFile("teacher.image", Constants.ATTACHMENT_TEMP_PATH,
				Constants.MAX_FILE_SIZE);

		// 保存文件并获取保存在数据库中的路径
		String savePath = UploadKit.saveAvatarImage(file.getFile());

		Teacher teacher = getModel(Teacher.class);

		System.out.println("savePath: " + savePath);

		// 设置头像路径
		teacher.set("image", savePath);

		// 设置教师为教课老师
		teacher.set("rid", 4);

		// 排序位置
		if (teacher.get("sort") == null || teacher.get("sort").equals("")) {
			teacher.set("sort", 1);
		}
		if (null == teacher.getInt("id")) {
			// 设置注册时间
			teacher.set("time", DateKit.getDateTime());

			teacher.set("uuid", UUID.randomUUID());
			teacher.save();
		} else {
			teacher.update();
		}

		redirect("/admin/teacher/index.html");
	}

	/**
	 * 跳转编辑页面
	 * 
	 */
	public void edit() {
		int teacherId = getParaToInt(0);
		setAttr("teacher", Teacher.dao.findById(teacherId));
		render("add.html");
	}

	/**
	 * 删除教师信息
	 */
	public void delete() {
		int teacherId = ParaKit.paramToInt(getPara(0), -1);

		if (teacherId > -1) {
			if (Teacher.dao.deleteById(teacherId)) {
				renderJson("msg", "删除成功！");
			}
		} else {
			renderJson("msg", "删除失败！");
		}

	}

}
