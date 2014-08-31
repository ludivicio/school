package my.school.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * School model.
 * 
 */

@SuppressWarnings("serial")
public class School extends Model<School> {
	public static final School dao = new School();

	/**
	 * 获取所有的学校
	 * 
	 * @return
	 */
	public List<School> getSchools() {
		return School.dao.find("select * from school order by id desc");
	}

	/**
	 * 获取学校的数量
	 * 
	 * @return
	 */
	public int getCount() {
		return Db.queryInt("select count(*) from school");
	}

	/**
	 * 获取分页数据
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<School> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from school order by id desc");
	}
}
