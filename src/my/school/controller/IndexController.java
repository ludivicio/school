package my.school.controller;

import my.school.validator.LoginValidator;

import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.Controller;

/**
 * IndexController
 * 
 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
public class IndexController extends Controller {

	public void index() {

	}

	@ClearInterceptor(ClearLayer.ALL)
	public void login() {
		render("/admin/login.html");
	}

	@ClearInterceptor(ClearLayer.ALL)
	@Before(LoginValidator.class)
	public void handle() {

		// 处理登录

	}

	/**
	 * 注销登录
	 */
	public void logout() {
		getSession().removeAttribute("admin");
		getSession().removeAttribute("doctor");
		getSession().removeAttribute("roleName");
		getSession().removeAttribute("permissions");
		redirect("/admin/index");
	}

	public void welcome() {
		render("welcome.html");
	}
}
