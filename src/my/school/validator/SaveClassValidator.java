package my.school.validator;




import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import my.school.model.Class;
/**
 * SaveClassValidator
 */
public class SaveClassValidator extends Validator {

	protected void validate(Controller controller) {

		// 是否填写了学校名称
		validateRequiredString("class.name", "nameMsg", "请输入班级名称!");
	
	}

	protected void handleError(Controller controller) {

		controller.keepModel(Class.class);

		String actionKey = getActionKey();
		System.out.println("actionKey: " + actionKey);
		controller.render("add.html");

	}
}
