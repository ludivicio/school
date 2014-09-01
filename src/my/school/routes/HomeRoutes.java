package my.school.routes;

import my.school.home.HomeController;

import com.jfinal.config.Routes;

public class HomeRoutes extends Routes {

	@Override
	public void config() {

		// 首页
		add("/", HomeController.class);

	}

}
