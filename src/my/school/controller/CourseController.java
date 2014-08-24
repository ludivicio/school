package my.school.controller;

import com.jfinal.core.Controller;

/**
 * CourseController
 * 
 * 课程管理
 * 
 */
public class CourseController extends Controller {
	
	
	public void index() {
		
		
		render("index.html");
	}

	public void add() {
		render("add.html");
	}

	/**
	 * 搜索
	 */
	public void search() {

		render("index.html");

	}

	/**
	 * 添加/修改科室信息处理方法
	 */
	public void save() {

		redirect("index.html");
	}

	/**
	 * 跳转编辑页面
	 * 
	 */
	public void edit() {
		
		render("add.html");
	}

	/**
	 * 删除科室信息
	 */
	public void delete() {
		
		redirect("index.html");
		
	}

	
}
