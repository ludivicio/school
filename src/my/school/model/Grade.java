package my.school.model;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

/**
 * Grade model.
 * 
 */
@SuppressWarnings("serial")
public class Grade extends Model<Grade> {
	public static final Grade dao = new Grade();

	private List<Integer> cids;

	/**
	 * 返回所有的年级信息
	 * 
	 * @return
	 */
	public List<Grade> getGrades() {
		return Grade.dao.find("select * from grade");
	}

	/**
	 * 删除该年级的所有课程
	 * 
	 * @return
	 */
	public boolean delCourses() {
		return Db.deleteById("assign", "gid", get("id"));
	}

	/**
	 * 获取该年级的所有课程ID
	 * 
	 * @return
	 */
	public List<Integer> getCourseIds() {

		if (cids == null) {
			cids = new ArrayList<Integer>();

			List<Assign> assigns = Assign.dao.find("select * from assign where gid = ?", get("id"));

			if (assigns != null) {

				for (Assign assign : assigns) {
					cids.add(assign.getInt("cid"));
				}
			}
		}

		return cids;
	}

	/**
	 * 判断该课程是否在该年级中
	 * 
	 * @param cid
	 * @return
	 */
	public boolean contain(int cid) {

		if (cids == null) {
			getCourseIds();
		}

		if (cids != null) {
			if (cids.contains(cid)) {
				return true;
			}
		}

		return false;
	}

	public void setCourseIds(List<Integer> cids) {
		this.cids = cids;
	}
}
