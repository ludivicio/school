package my.school.validator;

import my.school.model.Student;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.jfinal.validate.Validator;

/**
 * SaveDepartmentValidator
 */
public class SaveStudentValidator extends Validator {

	protected void validate(Controller controller) {

		// 表单中包含文件，所以先调用getFile方法，剩余的参数才可用
		UploadFile file = controller.getFile();
		if (file == null) {
			addError("imageMsg", "请选择学生的照片！");
		}

		// 是否填写了教师
		validateRequiredString("student.name", "nameMsg", "请输入学校名称!");
		validateRequiredString("student.national", "nationalMsg", "请输入所属!");
		validateRequired("student.identity", "identityMsg", "请输入合法的身份证号!");
		validateRegex("student.phone","1[0-9]{10}", "phoneMsg", "请输入合法的手机号!");
		validateRegex("student.birth","\\d{4}-\\d{2}-\\d{2}", "birthMsg", "请输入合法的出生日期!");
		validateString("student.birthplace", 6,50, "birthplaceMsg", "请输入6-50字的合法的籍贯");
		validateString("student.desc", 10,500, "descMsg", "请输入20-500之间的描述");
		validateString("student.address", 6,50, "addressMsg", "请输入6-50字的合法的地址");
		//System.out.println(controller.getPara("student.name")+controller.getPara("student.phone"));
	}

	protected void handleError(Controller controller) {

		controller.keepModel(Student.class);
		String actionKey = getActionKey();
		System.out.println("actionKey: " + actionKey);
		controller.render("add.html");

	}
}
