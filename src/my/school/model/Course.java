package my.school.model;

import java.util.List;

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
	 * 获取所有的课程信息
	 * 
	 * @return
	 */
	public List<Course> getCourses() {
		return Course.dao.find("select * from course");
	}

	/**
	 * 根据年级获取该年级所学的课程
	 * 
	 * @param gid
	 * @return
	 */
	public List<Course> getCoursesByGradeId(int gid) {
		return Course.dao.find(
				"select * from course where id in (select cid from assign where gid = ? )", gid);
	}

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
