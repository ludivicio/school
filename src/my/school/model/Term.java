package my.school.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * Register model.
 * 
 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */

@SuppressWarnings("serial")
public class Term extends Model<Term> {
	public static final Term dao = new Term();

	public Page<Term> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *",
				"from register order by id asc");
	}

	public Term getRegister(String userId, String doctorId, String date) {
		return dao
				.findFirst(
						"select * from register where userId = ? and doctorId=? and date = ?",
						userId, doctorId, date);
	}

	
	public Page<Term> paginateSig(int pageNumber, int pageSize,
			String userId) {
		return dao.paginate(pageNumber, pageSize, "select *",
				"from register where userId = ? ", userId);
	}

	public Page<Term> paginateForDoctor(int pageNumber, int pageSize,
			String doctorId, String verify) {

		// 审核状态（0:待审核，1:通过，2:未通过，3:用户取消预约）
		return dao.paginate(pageNumber, pageSize, "select *",
				"from register where doctorId = ? and verify = ?", doctorId,
				verify);
	}

	/**
	 * 获取科室
	 */
	public Grade getDepartment() {
		return Grade.dao.findById(get("departmentId"));
	}

	/**
	 * 获取医生姓名
	 */
	public Class getDoctor() {
		return Class.dao.findById(get("doctorId"));
	}

	/**
	 * 获取用户姓名
	 * 
	 */
	public Assign getUser() {
		return Assign.dao.findById(get("userId"));
	}
}
