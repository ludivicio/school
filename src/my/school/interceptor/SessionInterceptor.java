package my.school.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

/**
 * SessionInterceptor
 */
public class SessionInterceptor implements Interceptor {

	public void intercept(ActionInvocation ai) {

		ai.invoke();

	}
}
