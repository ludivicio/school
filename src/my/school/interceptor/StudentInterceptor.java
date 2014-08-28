package my.school.interceptor;

import java.util.List;

import my.school.model.School;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

/**
 * StudentInterceptor
 */
public class StudentInterceptor implements Interceptor {

	public void intercept(ActionInvocation ai) {

		Controller controller = ai.getController();

		ai.invoke();

		// 读取所有学校信息
		List<School> schoolList = School.dao.find("select * from school");
		controller.setAttr("schoolList", schoolList);

	}
}
