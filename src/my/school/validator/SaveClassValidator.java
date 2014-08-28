package my.school.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import my.school.model.Class;

/**
 * SaveDepartmentValidator
 */
public class SaveClassValidator extends Validator {

	protected void validate(Controller controller) {

		// 是否选择了学校
		validateRequired("class.sid", "errorMsg", "数据不完整！");

		// 是否选择了班主任
		validateRequired("class.tid", "errorMsg", "数据不完整!");

	}

	protected void handleError(Controller controller) {

		controller.keepModel(Class.class);

		String actionKey = getActionKey();
		System.out.println("actionKey: " + actionKey);

		controller.setAttr("status", "error");
		controller.renderJson();

	}
}
