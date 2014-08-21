package my.school.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * Post model.
 * 
 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */

@SuppressWarnings("serial")
public class Course extends Model<Course> {
	public static final Course dao = new Course();

	/**
	 * 获取当前文章的分类信息
	 * 
	 * @return
	 */
	public School getCategory() {
		return School.dao.findById(get("cid"));
	}

	/**
	 * 获取最新发布的10条公告通知
	 * @return
	 */
	public List<Course> getNew(int cid, int size) {
		return Course.dao.find("select * from post where cid = ? order by time desc limit 0, ?", cid, size);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<Course> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from post order by time desc");
	}

	/**
	 * 带分类分页获取数据
	 */
	public Page<Course> paginate(int pageNumber, int pageSize, int cid) {

		return paginate(pageNumber, pageSize, "select *",
				"from post where cid = ? order by time desc", cid);

	}

}
