package my.school.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * Term model.
 * 
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

	public Page<Term> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from term order by id desc");
	}

}
