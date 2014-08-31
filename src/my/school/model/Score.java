package my.school.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * Score model.
 * 
 */

@SuppressWarnings("serial")
public class Score extends Model<Score> {
	public static final Score dao = new Score();
	private  Term term;
	private Course course;
	private Student student;
	public Page<Score> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from score order by id desc");
	}
	
	public List<Score> getList(String studentId){
		return Score.dao.find("select * from score where sid  = ", studentId);
	}

	public Term getTerm() {
		if (term == null) {
			term = Term.dao.findById(get("tid"));
		}
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	public Course getCourse() {
		if (course == null) {
			course = Course.dao.findById(get("cid"));
		}
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Student getStudent() {
		if (student == null) {
			student = Student.dao.findById(get("sid"));
		}
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	
}
