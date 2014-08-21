package my.school.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * Permission model.
 * 
 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */

@SuppressWarnings("serial")
public class Permission extends Model<Permission> {
	public static final Permission dao = new Permission();

	public List<Permission> subPermissions;

	public List<Permission> initializeSubPermissions() {

		if (subPermissions != null) {
			return subPermissions;
		}

		String sql = "select * from permission where pid = ? order by id asc";
		subPermissions = Permission.dao.find(sql, get("id"));

		return subPermissions;
	}

	public Page<Permission> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from blog order by id asc");
	}
}
