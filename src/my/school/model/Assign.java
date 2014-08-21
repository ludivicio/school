package my.school.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * User model.
 * 
 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */

@SuppressWarnings("serial")
public class Assign extends Model<Assign> {
	public static final Assign dao = new Assign();

	
	/**
	 * 根据账号和密码判断是否登录成功
	 * @param account
	 * @param password
	 * @return
	 */
	public Assign getByAccountAndPassword(String account, String password) {
		return dao.findFirst("select * from user where account = ? and password = ?", account,
				password);
	}
	
	
	
	
	
	public Page<Assign> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from user order by id asc");
	}
}
