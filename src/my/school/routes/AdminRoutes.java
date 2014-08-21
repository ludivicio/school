package my.school.routes;

import my.school.controller.IndexController;

import com.jfinal.config.Routes;

public class AdminRoutes extends Routes {

	@Override
	public void config() {

		// 后台首页
		add("/admin", IndexController.class);

	}

}
