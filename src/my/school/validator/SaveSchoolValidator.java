package my.school.validator;

import my.school.model.School;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.jfinal.validate.Validator;

/**
 * SaveDepartmentValidator
 */
public class SaveSchoolValidator extends Validator {

	protected void validate(Controller controller) {

		// 表单中包含文件，所以先调用getFile方法，剩余的参数才可用
		UploadFile file = controller.getFile();
		if (file == null) {
			addError("imageMsg", "请选择学校的图片！");
		}

		// 是否填写了学校名称
		validateRequiredString("school.name", "nameMsg", "请输入学校名称!");

		// 0 ~ 255之间的整数为合法排序值
		validateRequiredString("school.desc", "descMsg", "请输入学校简介!");
		validateRequiredString("school.address", "addressMsg", "请输入地址信息!");
		validateRequiredString("school.rector", "rectorMsg", "请输入校长姓名!");
		validateRequiredString("school.phone", "phoneMsg", "请输入电话号码!");

	}

	protected void handleError(Controller controller) {

		controller.keepModel(School.class);

		String actionKey = getActionKey();
		System.out.println("actionKey: " + actionKey);
		controller.render("add.html");

	}
}
