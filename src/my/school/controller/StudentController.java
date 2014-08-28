package my.school.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my.school.config.Constants;
import my.school.interceptor.StudentInterceptor;
import my.school.kit.DateKit;
import my.school.kit.ParaKit;
import my.school.kit.UUID;
import my.school.kit.UploadKit;
import my.school.model.Class;
import my.school.model.Student;
import my.school.validator.SaveStudentValidator;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

/**
 * StudentController
 * 
 * 学生管理
 * 
 */
@Before(StudentInterceptor.class)
public class StudentController extends Controller {
	
	
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
		Page<Student> studentList = Student.dao.paginate(page, Constants.PAGE_SIZE);
		setAttr("studentList", studentList);
		setAttr("searchUuid", "");
		setAttr("searchName", "");
		setAttr("searchSex",-1);
		setAttr("searchFeature", -1);
		setAttr("searchPage", Constants.NOT_SEARCH_PAGE);
		render("index.html");
	}

	public void add() {
		render("add.html");
	}
	
	public void getClassBySchool(){
		int schoolId = getParaToInt("schoolId");
		if(schoolId>0){
			List<Class> classList= Class.dao.getClassListBySid(schoolId);
			if(classList.size()>0){
				
				setAttr("classList",classList);
				setAttr("status", "success");
			}else{
				setAttr("status", "failed");
			}
		}else{
			setAttr("status", "failed");
		}
		
		renderJson();
		
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
			queryParams.put("feature", getPara("feature"));

			setSessionAttr(Constants.SEARCH_SESSION_KEY, queryParams);

		}

		int page = ParaKit.paramToInt(getPara("p"), 1);

		if (page < 1) {
			page = 1;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("from student where id > 0");

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

			if (sex>-1) {
				sb.append(" and sex like ?");
				params.add("%" + sex + "%");
			}

			int feature = Integer.valueOf(queryParams.get("feature").toString());

			if (feature>-1) {
				sb.append(" and feature like ?");
				params.add("%" + feature + "%");
			}
			setAttr("searchUuid", uuid);
			setAttr("searchName", name);
			setAttr("searchSex", sex);
			setAttr("searchFeature", feature);
			;
			setAttr("searchPage", Constants.SEARCH_PAGE);

		}

		// 学生列表
		Page<Student> studentList = Student.dao.paginate(page, Constants.PAGE_SIZE, "select *",
				sb.toString(), params.toArray());

		setAttr("studentList", studentList);

		render("index.html");

	}

	/**
	 * 添加/修改科室信息处理方法
	 */
	@Before(SaveStudentValidator.class)
	public void save() {
		UploadFile file = getFile("student.image", Constants.ATTACHMENT_TEMP_PATH,
				Constants.MAX_FILE_SIZE);

		// 保存文件并获取保存在数据库中的路径
		String savePath = UploadKit.saveAvatarImage(file.getFile());

		Student student= getModel(Student.class);

		System.out.println("savePath: " + savePath);

		// 设置头像路径
		student.set("image", savePath);

		//排序位置
		if(student.get("sort") == null || student.get("sort").equals("")){
			student.set("sort",1);
		}
		if (null == student.getInt("id")) {
			//设置注册时间
			student.set("time", DateKit.getDateTime());
			student.set("uuid", UUID.randomUUID());
			student.save();
		} else {
			student.update();
		}

		redirect("index.html");
	}

	/**
	 * 跳转编辑页面
	 * 
	 */
	public void edit() {
		int studentId = getParaToInt(0);
		setAttr("student",Student.dao.findById(studentId));
		render("add.html");
	}

	/**
	 * 删除科室信息
	 */
	public void delete() {
		
		int studentId = ParaKit.paramToInt(getPara(0), -1);
		
		if(studentId>-1){
			if(Student.dao.deleteById(studentId)){
				renderJson("msg","删除成功！");
			}
		}else{
			renderJson("msg","删除失败！");
		}
	
	}

	
}
