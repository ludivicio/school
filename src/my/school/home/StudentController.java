package my.school.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my.school.config.Constants;
import my.school.kit.ParaKit;
import my.school.model.Score;
import my.school.model.Student;

import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

/**
 * HomeController
 * 
 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
@ClearInterceptor(ClearLayer.ALL)
public class StudentController extends Controller {

	public void index() {
		
		System.out.println("here");
		
		setAttr("title", "首页");
		
		render("index.html");
	}
	
	public void search(){
		String uuid = getPara("uuid");
		String name = getPara("name");
		String identity = getPara("identity");
		StringBuilder sb = new StringBuilder();
		sb.append("select * from student where id > 0");
		List<Object> params = new ArrayList<Object>();
		
			if(!ParaKit.isEmpty(uuid)){
				sb.append(" and uuid = ?");
				params.add(uuid);
			}
			if (!ParaKit.isEmpty(name)) {
				sb.append(" and name like ?");
				params.add("%" + name + "%");
			}
			if (!ParaKit.isEmpty(identity)) {
				sb.append(" and identity = ?");
				params.add(identity);
			}
			
			setAttr("searchUuid", uuid);
			setAttr("searchName", name);
			setAttr("searchIdentity", identity);
			
		// 学生列表
		List<Student> studentList = Student.dao.find(sb.toString(), params.toArray());
		if(studentList.size()> 0){
			Student student = studentList.get(0);
			List<Score> scoreList = null;
			if(student != null){
				scoreList = Score.dao.getList(student.get("id").toString());
			}
			setAttr("student", student);
			setAttr("scoreList",scoreList);
		}
		render("/index.html");
	}

}
