package my.school.config;

import my.school.beetl.function.CheckFunction;
import my.school.beetl.function.ExistFunction;
import my.school.interceptor.SessionInterceptor;
import my.school.model.Admin;
import my.school.model.Assign;
import my.school.model.Class;
import my.school.model.Course;
import my.school.model.Grade;
import my.school.model.Permission;
import my.school.model.Role;
import my.school.model.RolePermission;
import my.school.model.School;
import my.school.model.Score;
import my.school.model.Student;
import my.school.model.Teacher;
import my.school.model.Term;
import my.school.routes.AdminRoutes;
import my.school.routes.HomeRoutes;

import org.bee.tl.core.GroupTemplate;
import org.bee.tl.ext.EmptyFunction;
import org.bee.tl.ext.jfinal.BeetlRenderFactory;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.handler.FakeStaticHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;

/**
 * API引导式配置
 */
public class CoreConfig extends JFinalConfig {

	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用getProperty(...)获取值
		loadPropertyFile("db_config.txt");

		me.setDevMode(getPropertyToBoolean("devMode", false));

		// me.setError404View("/error/404.html");

		// 使用beetl作为渲染引擎
		me.setMainRenderFactory(new BeetlRenderFactory());

		// 获取GroupTemplate ,可以设置共享变量等操作
		GroupTemplate groupTemplate = BeetlRenderFactory.groupTemplate;

		groupTemplate.registerFunction("empty", new EmptyFunction());
		
		// 注册自定义方法
		groupTemplate.registerFunction("exist", new ExistFunction());
		groupTemplate.registerFunction("check", new CheckFunction());
		
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add(new AdminRoutes());
		me.add(new HomeRoutes());
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {

		// DruidPlugin
		DruidPlugin dp = new DruidPlugin(getProperty("jdbcUrl"), getProperty("user"),
				getProperty("password"));

		dp.addFilter(new StatFilter());
		WallFilter wall = new WallFilter();
		wall.setDbType("mysql");
		dp.addFilter(wall);
		me.add(dp);

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);

		arp.setShowSql(true);

		me.add(arp);

		// 配置属性名(字段名)大小写不敏感容器工厂
		arp.setContainerFactory(new CaseInsensitiveContainerFactory());

		// 映射模型
		arp.addMapping("admin", Admin.class);
		arp.addMapping("assign", Assign.class);
		arp.addMapping("class", Class.class);
		arp.addMapping("course", Course.class);
		arp.addMapping("grade", Grade.class);
		arp.addMapping("permission", Permission.class);
		arp.addMapping("role", Role.class);
		arp.addMapping("role_permission", "rid", RolePermission.class);
		arp.addMapping("school", School.class);
		arp.addMapping("score", Score.class);
		arp.addMapping("student", Student.class);
		arp.addMapping("teacher", Teacher.class);
		arp.addMapping("term", Term.class);

	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {

		me.add(new SessionInterceptor());

	}

	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {

		// 配置伪静态
		me.add(new FakeStaticHandler());

		// 配置上下文路径
		me.add(new ContextPathHandler());

		me.add(new DruidStatViewHandler("/druid"));
	}

}
