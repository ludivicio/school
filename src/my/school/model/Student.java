package my.school.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * Student model.
 * 
 */

@SuppressWarnings("serial")
public class Student extends Model<Student> {
	public static final Student dao = new Student();

	public Page<Student> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from student");
	}

	public Page<Student> paginate(int pageNumber, int pageSize, int cid) {
		return paginate(pageNumber, pageSize, "select *", "from student where cid = ?", cid);
	}
}
