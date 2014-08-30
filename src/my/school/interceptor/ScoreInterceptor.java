package my.school.interceptor;

import java.util.List;

import my.school.model.School;
import my.school.model.Term;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

/**
 * DoctorInterceptor
 */
public class ScoreInterceptor implements Interceptor {

	public void intercept(ActionInvocation ai) {

		Controller controller = ai.getController();

		ai.invoke();

		List<Term> termList = Term.dao.getTerms();
		controller.setAttr("termList", termList);

		List<School> schoolList = School.dao.getSchools();
		controller.setAttr("schoolList", schoolList);

	}
}
