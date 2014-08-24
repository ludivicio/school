package my.school.home;

import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.Controller;

/**
 * HomeController
 * 
 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
@ClearInterceptor(ClearLayer.ALL)
public class HomeController extends Controller {

	public void index() {
		
		System.out.println("here");
		
		setAttr("title", "首页");
		
		render("index.html");
	}

}
