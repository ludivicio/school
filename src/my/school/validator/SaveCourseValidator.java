package my.school.validator;




import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import my.school.model.Course;
/**
 * SaveClassValidator
 */
public class SaveCourseValidator extends Validator {

	protected void validate(Controller controller) {

		// 是否填写了课程名称
		validateRequiredString("course.name", "nameMsg", "请输入课程名称!");
		validateString("course.remark", 20,500,"remarkMsg", "请输入20-500之间的课程名称!");
	
	}

	protected void handleError(Controller controller) {

		controller.keepModel(Course.class);

		String actionKey = getActionKey();
		System.out.println("actionKey: " + actionKey);
		controller.render("add.html");

	}
}
