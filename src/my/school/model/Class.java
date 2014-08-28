package my.school.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * Class model.
 * 
 */

@SuppressWarnings("serial")
public class Class extends Model<Class> {
	public static final Class dao = new Class();

	private Teacher teacher;

	private School school;

	/**
	 * 获取所有班级
	 * 
	 * @return
	 */
	public List<Class> getClasses() {
		return Class.dao.find("select * from class order by sort desc");
	}

	/**
	 * 获取特定年份，特定学校下班级的数量
	 * 
	 * @param year
	 * @param sid
	 * @return
	 */
	public int getCount(String year, String sid) {

		List<Class> classes = Class.dao.find("select * from class where year = ? and sid = ?",
				year, sid);
		return classes.size();

	}

	
	/**
	 * 根据编号获取班主任名称
	 */
	public String getTeacherName() {

		Teacher teacher = Teacher.dao.findById(get("id"));
		if (teacher != null) {
			return teacher.get("name");
		} else {
			return "--";
		}
	}

	/**
	 * 根据编号获取学校
	 */
	public String getSchoolName() {
		School school = School.dao.findById(get("id"));
		if (school != null) {
			return school.get("name");
		} else {
			return "--";
		}

	}

	/**
	 * 根据SchoolId获取class列表
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<Class> getClassListBySid(int sid) {
		return Class.dao.find("select * from class where sid = ?", sid);
	}

	public Page<Class> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from class order by sort desc");
	}

	public Teacher getTeacher() {

		if (teacher == null) {
			teacher = Teacher.dao.findById(get("tid"));
		}

		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public School getSchool() {

		if (school == null) {
			school = School.dao.findById(get("id"));
		}

		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}
}
