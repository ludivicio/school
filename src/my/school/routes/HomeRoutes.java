package my.school.routes;

import my.school.home.HomeController;
import my.school.home.StudentController;
import my.school.home.TeacherController;

import com.jfinal.config.Routes;

public class HomeRoutes extends Routes {

	@Override
	public void config() {

		// 首页
		add("/", HomeController.class);
		add("/student", StudentController.class);
		add("/teacher", TeacherController.class);

	}

}
