package my.school.home;

import java.text.ParseException;
import java.util.List;

import my.school.kit.DateKit;
import my.school.model.Class;
import my.school.model.Course;
import my.school.model.Student;
import my.school.model.Teacher;
import my.school.model.Term;

import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.Controller;

/**
 * HomeController
 * 
 */
@ClearInterceptor(ClearLayer.ALL)
public class HomeController extends Controller {

	public void index() {

		setAttr("title", "佳木斯市中小学学籍查询入口");

		render("index.html");
	}

	public void teacher() {

		setAttr("title", "佳木斯市中小学学籍查询入口");

		render("teacher.html");
	}

	public void search() {

		String type = getPara("type");

		System.out.println("type: " + type);

		if ("student".equals(type)) {

			String uuid = getPara("uuid");

			String name = getPara("name");

			String identity = getPara("identity");

			setAttr("searchUuid", uuid);
			setAttr("searchName", name);
			setAttr("searchIdentity", identity);

			Student student = Student.dao.getStudent(uuid, name, identity);

			if (student == null) {

				setAttr("errorMsg", "未找到该学生的相关信息！");

				render("index.html");

				return;
			}

			setAttr("student", student);

			Class clazz = student.getClazz();

			Term term = Term.dao.getLastTerm();

			setAttr("term", term);

			// 获取该学期下该班级的所有课程信息

			String end = term.getStr("end");

			System.out.println("end: " + end);

			int gid = -1;

			try {

				int year = DateKit.getYear("yyyy年M月", end);
				int month = DateKit.getMonth("yyyy年M月", end);

				System.out.println("year: " + year + "  month: " + month);

				String startYear = clazz.getStr("year");
				gid = year - Integer.parseInt(startYear);

				System.out.println("grade: " + gid);

			} catch (ParseException e) {
				e.printStackTrace();
			}

			// 这里使用阿拉伯数字的年级来代表年级的ID

			List<Course> courseList = Course.dao.getCoursesByGradeId(gid);
			setAttr("courseList", courseList);

			render("index.html");

		} else if ("teacher".equals(type)) {

			String uuid = getPara("uuid");

			String name = getPara("name");

			String identity = getPara("identity");

			setAttr("searchUuid", uuid);
			setAttr("searchName", name);
			setAttr("searchIdentity", identity);

			Teacher teacher = Teacher.dao.getTeacher(uuid, name, identity);

			if (teacher == null) {

				setAttr("errorMsg", "未找到该教师的相关信息！");

				render("index.html");

				return;
			}

			setAttr("teacher", teacher);

			render("teacher.html");
			
		}

	}

}
