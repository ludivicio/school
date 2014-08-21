package my.school.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * PostCategory model.
 * 
 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */

@SuppressWarnings("serial")
public class School extends Model<School> {
	public static final School dao = new School();

	/**
	 * 获取所有的子分类
	 * 
	 * @return
	 */
	public List<School> getCategories() {
		return School.dao.find("select * from post_category where pid > 0");
	}

	/**
	 * 根据id获取所有的子分类
	 * 
	 * @param id
	 * @return
	 */
	public List<School> getSubCategoriesById(int id) {
		return School.dao.find("select * from post_category where pid = ?", id);
	}

	/**
	 * 获取当前分类的所有子分类
	 * 
	 * @return
	 */
	public List<School> getSubCategories() {
		return School.dao.find("select * from post_category where pid = ?", get("id"));
	}

	/**
	 * 获取分页数据
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<School> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from post order by id asc");
	}
}
