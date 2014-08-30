package my.school.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * Teacher model.
 * 
 */

@SuppressWarnings("serial")
public class Teacher extends Model<Teacher> {

	private School school;

	public static final Teacher dao = new Teacher();

	/**
	 * 根据学校ID所有该学校所有的教师信息
	 * 
	 * @return
	 */
	public List<Teacher> getTeachers(int sid) {
		return Teacher.dao.find("select * from teacher where sid = ?", sid);
	}

	/**
	 * 根据学校ID获取该学校所有的班主任信息
	 * 
	 * @return
	 */
	public List<Teacher> getHeadTeachers(int sid) {
		return Teacher.dao.find("select * from teacher where rid = 3 and sid = ?", sid);
	}

	/**
	 * 根据学校ID获取该学校所有的任课老师信息
	 * 
	 * @return
	 */
	public List<Teacher> getNormalTeachers(int sid) {
		return Teacher.dao.find("select * from teacher where rid = 4 and sid = ?", sid);
	}

	/**
	 * 根据班级ID获取班主任信息
	 * 
	 * @param cid
	 * @return
	 */
	public Teacher getTeachersByClassId(int cid) {
		Class clazz = Class.dao.findById(cid);
		return clazz.getTeacher();
	}

	/**
	 * 分页处理
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<Teacher> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from teacher order by id desc");
	}

	public School getSchool() {

		if (school == null) {
			school = School.dao.findById(get("sid"));
		}
		System.out.println("school.name = " + school.getStr("name"));
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

}
