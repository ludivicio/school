package my.school.interceptor;

import java.util.ArrayList;
import java.util.List;

import my.school.kit.DateKit;
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

		// 生成入学年份，从2001年开始
		List<String> years = new ArrayList<String>();
		int currentYear = DateKit.getYear();
		for (int i = 2001; i <= currentYear; i++) {
			years.add(String.valueOf(i));
		}

		controller.setAttr("yearList", years);

		// 读取所有学校信息
		List<School> schoolList = School.dao.find("select * from school");
		controller.setAttr("schoolList", schoolList);

		System.out.println("schoolList.size = " + schoolList.size());
		
		// 读取所有任课教师的信息
		List<Teacher> teacherList = Teacher.dao.find("select * from teacher where rid=4");
		controller.setAttr("teacherList", teacherList);

	}
}
