package my.school.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * Score model.
 * 
 */

@SuppressWarnings("serial")
public class Score extends Model<Score> {
	public static final Score dao = new Score();

	public Page<Score> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from score order by id desc");
	}
}
