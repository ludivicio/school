package my.school.home;

import java.util.ArrayList;
import java.util.List;

import my.school.kit.ParaKit;
import my.school.model.Score;
import my.school.model.Student;
import my.school.model.Teacher;

import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.Controller;

/**
 * HomeController
 * 
 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
@ClearInterceptor(ClearLayer.ALL)
public class TeacherController extends Controller {

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
		sb.append("select * from teacher where id > 0");
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
		List<Teacher> teacherList = Teacher.dao.find(sb.toString(), params.toArray());
		if(teacherList.size()> 0){
			Teacher teacher = teacherList.get(0);
			setAttr("teacher", teacher);
		}
		render("/index.html");
	}

}
