package my.school.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * Student model.
 * 
 */

@SuppressWarnings("serial")
public class Student extends Model<Student> {
	public static final Student dao = new Student();

	private School school;

	private Class clazz;

	/**
	 * 获取该班级学生的数量
	 * 
	 * @param cid
	 * @return
	 */
	public int getCountByClassId(int cid) {
		return Db.queryInt("select count(*) from student where cid = ?", cid);
	}

	/**
	 * 根据uuid,name,identity来获取学生信息
	 * 
	 * @param uuid
	 * @param name
	 * @param identity
	 * @return
	 */
	public Student getStudent(String uuid, String name, String identity) {
		return Student.dao.findFirst(
				"select * from student where uuid = ? and name = ? and identity = ?", uuid, name,
				identity);
	}

	/**
	 * 获取该学生某个学期某个课程的成绩
	 * 
	 * @param tid
	 * @param cid
	 * @return
	 */
	public String getScore(int tid, int cid) {

		System.out.println("tid: " + tid + " cid: " + cid);

		Integer score = Db.queryInt(
				"select score from score where sid = ? and tid = ? and cid = ? ", get("id"), tid,
				cid);

		if (score == null) {
			return "";
		}

		return score.toString();
	}

	public Page<Student> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from student");
	}

	public Page<Student> paginate(int pageNumber, int pageSize, int cid) {
		return paginate(pageNumber, pageSize, "select *", "from student where cid = ?", cid);
	}

	public School getSchool() {

		if (school == null) {
			school = School.dao.findById(get("sid"));
		}
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public Class getClazz() {
		if (clazz == null) {
			clazz = Class.dao.findById(get("cid"));
		}
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}
}
