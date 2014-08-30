package my.school.validator;

import my.school.model.Teacher;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.jfinal.validate.Validator;

/**
 * SaveDepartmentValidator
 */
public class SaveTeacherValidator extends Validator {

	protected void validate(Controller controller) {

		// 表单中包含文件，所以先调用getFile方法，剩余的参数才可用
		UploadFile file = controller.getFile();
		if (file == null) {
			addError("imageMsg", "请选择教师的照片！");
		}

		// 是否填写了教师
		validateRequiredString("teacher.name", "nameMsg", "请输入学校名称!");
		validateRequiredString("teacher.national", "nationalMsg", "请输入所属!");
		
		validateRequired("teacher.identity", "identityMsg", "请输入合法的身份证号!");
		validateRegex("teacher.birth","\\d{4}-\\d{2}-\\d{2}", "birthMsg", "请输入合法的出生日期!");
		validateString("teacher.birthplace", 6,50, "birthplaceMsg", "请输入6-50字的合法的籍贯");
		validateString("teacher.desc", 10,500, "descMsg", "请输入20-500之间的描述");
		validateString("teacher.address", 6,50, "addressMsg", "请输入6-50字的合法的地址");
		validateInteger("teacher.seniority",1, 45,"seniorityMsg", "请输合法的教龄教龄介于1-45!");
		validateRegex("teacher.phone","1[0-9]{10}", "phoneMsg", "请输入合法的手机号!");
		validateEmail("teacher.email", "emailMsg", "请输入合法的email!");

		
	}

	protected void handleError(Controller controller) {

		controller.keepModel(Teacher.class);

		String actionKey = getActionKey();
		System.out.println("actionKey: " + actionKey);
		controller.render("add.html");

	}
}
