package my.school.model;

import java.util.List;

import my.school.config.Constants;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * Doctor model.
 * 
 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */

@SuppressWarnings("serial")
public class Class extends Model<Class> {
	public static final Class dao = new Class();

	/**
	 * 获取所有班级
	 * @return
	 */
	public List<Class> getDoctors(){
		return Class.dao.find("select * from class order by sort desc");
	}
	
	public Class getByAccountAndPassword(String account, String password) {

		String encodePassword = password;// MD5.digest(password);

		// System.out.println("encodePassword: " + encodePassword);

		return dao.findFirst(
				"select * from class where account = ? and password = ?",
				account, encodePassword);
	}
	
	/**
	 * 获取角色信息
	 * 
	 * @return
	 */
	public Role getRole() {
		return Role.dao.findById(get("roleId"));
	}

	/**
	 * 根据编号获取班主任
	 */
	public Teacher getTeacher(){
		List<Teacher> teacher = Teacher.dao.find("select * from teacher where uuid =?",get("tuuid"));
		return teacher.get(0);
	}
	/**
	 * 根据编号获取学校
	 */
	public School getSchool(){
		List<School> school = School.dao.find("select * from school where uuid =?",get("suuid"));
		return school.get(0);
	}
	
	/**
	 * 获取管理员的所有权限
	 * 
	 * @return
	 */
	public List<Permission> getPermissions() {
		String sql = "select * from permission where id in (select permissionId from role_permission where roleId = ?)";
		return Permission.dao.find(sql, get("roleId"));
	}


	/**
	 * 根据排序值获取首页显示的医生
	 * 
	 * @return
	 */
	public List<Class> getRecommends() {
		return Class.dao
				.find("select * from class where recommend = 1 order by sort desc limit 0, ?",
						Constants.RECOMMEND_DOCTOR_SIZE);
	}



	public Page<Class> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *",
				"from class order by sort desc");
	}
}
