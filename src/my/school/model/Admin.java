package my.school.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

/**
 * Admin model.
 * 
 */

@SuppressWarnings("serial")
public class Admin extends Model<Admin> {
	public static final Admin dao = new Admin();

	private String roleName;
	
	public Admin getByAccountAndPassword(String account, String password) {

		return dao.findFirst("select * from admin where account = ? and password = ?", account,
				password);
	}

	/**
	 * 根据tid获取管理员信息
	 * @param tid
	 * @return
	 */
	public Admin getAdminByTeacherId(int tid) {
		
		return Admin.dao.findFirst("select * from admin where rid = 3 and tid = ?", tid);
	}
	
	
	/**
	 * 获取角色信息
	 * 
	 * @return
	 */
	public Role getRole() {
		Role role = Role.dao.findById(get("rid"));
		roleName = role.getStr("name");
		return role;
	}

	/**
	 * 获取管理员的所有权限
	 * 
	 * @return
	 */
	public List<Permission> getPermissions() {
		String sql = "select * from permission where id in ( select pid from role_permission where rid = ?) order by id asc";
		return Permission.dao.find(sql, get("rid"));
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
