package my.school.model;

import java.util.List;

import my.school.config.Constants;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * Department model.
 * 
 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
@SuppressWarnings("serial")
public class Grade extends Model<Grade> {
	public static final Grade dao = new Grade();

	/**
	 * 返回所有的科室信息
	 * 
	 * @return
	 */
	public List<Grade> getDepartments() {
		return Grade.dao.find("select * from department");
	}

	/**
	 * 获取当前科室主任医师的信息
	 */
	public Class getDirector() {
		return Class.dao.findById(get("directorId"));
	}

	/**
	 * 获取当前科室下所有的医生信息
	 */
	public List<Class> getDoctors() {
		return getDoctors(Integer.parseInt(String.valueOf(get("id"))));
	}

	/**
	 * 获取某科室下所有的医生信息
	 * 
	 * @param departmentId
	 * @return
	 */
	public List<Class> getDoctors(int departmentId) {
		return Class.dao.find("select * from doctor where departmentId = ?", departmentId);
	}

	/**
	 * 根据排序值获取首页要显示的科室
	 * @return
	 */
	public List<Grade> getRecommends() {
		return Grade.dao.find("select * from department where recommend = 1 order by sort desc limit 0, ?", Constants.RECOMMEND_DEPARTMENT_SIZE);
	}
	
	
	public Page<Grade> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from department order by recommend desc, sort desc");
	}
}
