package my.school.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

/**
 * Register model.
 * 
 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */

@SuppressWarnings("serial")
public class Term extends Model<Term> {
	public static final Term dao = new Term();

	/**
	 * 获取所有的学期数据
	 * 
	 * @return
	 */
	public List<Term> getTerms() {
		return dao.find("select * from term order by id desc");
	}

	/**
	 * 获取最后一条记录
	 * 
	 * @return
	 */
	public Term getLastTerm() {

		return dao.findFirst("select * from term order by id desc");

	}

}
