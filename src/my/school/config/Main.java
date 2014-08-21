package my.school.config;

import com.jfinal.core.JFinal;

public class Main {

	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目 
	 * 运行此 main 方法可以启动项目
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 8080, "/", 5);
	}
}
