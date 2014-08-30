package my.school.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * Assign model.
 * 
 */

@SuppressWarnings("serial")
public class Assign extends Model<Assign> {
	public static final Assign dao = new Assign();

	/**
	 * 判断数据库中是否已经存在数据
	 * 
	 * @param gid
	 * @param cid
	 * @return
	 */
	public boolean isExist(String gid, String cid) {
		Assign assign = Assign.dao.findFirst("select * from assign where gid = ? and cid = ?", gid,
				cid);
		if (assign != null) {
			return true;
		}
		return false;
	}

}
