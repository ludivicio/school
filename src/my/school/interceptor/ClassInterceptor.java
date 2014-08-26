package my.school.interceptor;

import java.util.List;

import my.school.model.School;
import my.school.model.Teacher;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

/**
 * DoctorInterceptor
 */
public class ClassInterceptor implements Interceptor {

	public void intercept(ActionInvocation ai) {
		
		Controller controller = ai.getController();
		
		ai.invoke();
		
		// 读取所有学校信息
		List<School> schoolList = School.dao.find("select * from school");
		controller.setAttr("schoolList", schoolList);
		
		//读取所有教师信息
		List<Teacher> teacherList = Teacher.dao.find("select * from teacher where rid=?",3);
		controller.setAttr("teacherList", teacherList);

	}
}
