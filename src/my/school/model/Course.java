package my.school.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * Course model.
 * 
 */

@SuppressWarnings("serial")
public class Course extends Model<Course> {
	public static final Course dao = new Course();

	/**
	 * 分页获取数据
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<Course> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from course order by id desc");
	}

	/**
	 * 带分类分页获取数据
	 */
	public Page<Course> paginate(int pageNumber, int pageSize, int cid) {

		return paginate(pageNumber, pageSize, "select *",
				"from course where cid = ? order by id desc", cid);

	}

}
