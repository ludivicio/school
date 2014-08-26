package my.school.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my.school.config.Constants;
import my.school.kit.ParaKit;
import my.school.kit.UUID;
import my.school.kit.UploadKit;
import my.school.model.School;
import my.school.validator.SaveSchoolValidator;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

/**
 * SchoolController
 * 
 * 学校管理
 * 
 */
public class SchoolController extends Controller {

	public void index() {

		// 判断当前是否是搜索的数据进行的分页
		// 如果是搜索的数据，则跳转至search方法处理
		if (!ParaKit.isEmpty(getPara("s"))) {

			search();

			return;
		}

		int page = ParaKit.paramToInt(getPara("p"), 1);

		if (page < 1) {
			page = 1;
		}

		// 读取所有的学校信息
		Page<School> schoolList = School.dao.paginate(page, Constants.PAGE_SIZE);
		setAttr("schoolList", schoolList);
		setAttr("searchUuid", "");
		setAttr("searchName", "");
		setAttr("searchRecotr", "");
		setAttr("searchPage", Constants.NOT_SEARCH_PAGE);

		render("index.html");
	}

	public void add() {
		render("add.html");
	}

	/**
	 * 搜索
	 */
	public void search() {

		if (ParaKit.isEmpty(getPara("s"))) {

			// 说明当前请求是搜索数据的post请求，并非搜索的分页请求
			// 在这里执行搜索操作，并将结果保存到缓存中

			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("uuid", getPara("uuid"));
			queryParams.put("rector", getPara("recotr"));
			queryParams.put("name", getPara("name"));
			setSessionAttr(Constants.SEARCH_SESSION_KEY, queryParams);

		}

		int page = ParaKit.paramToInt(getPara("p"), 1);

		if (page < 1) {
			page = 1;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("from school where id > 0");

		HashMap<String, String> queryParams = getSessionAttr(Constants.SEARCH_SESSION_KEY);
		List<Object> params = new ArrayList<Object>();

		if (queryParams != null) {

			String uuid = queryParams.get("uuid");

			if (!ParaKit.isEmpty(uuid)) {
				sb.append(" and uuid like ?");
				params.add("%" + uuid + "%");
			}

			String name = queryParams.get("name");

			if (!ParaKit.isEmpty(name)) {
				sb.append(" and name like ?");
				params.add("%" + name + "%");
			}

			String rector = queryParams.get("rector");

			if (!ParaKit.isEmpty(rector)) {
				sb.append(" and rector like ?");
				params.add("%" + rector + "%");
			}

			setAttr("searchUuid", uuid);
			setAttr("searchName", name);
			setAttr("searchRecotr", rector);
			setAttr("searchPage", Constants.SEARCH_PAGE);

		}

		// 医生列表
		Page<School> schoolList = School.dao.paginate(page, Constants.PAGE_SIZE, "select *",
				sb.toString(), params.toArray());

		setAttr("schoolList", schoolList);

		render("index.html");

	}

	/**
	 * 添加/修改学校信息处理方法
	 */
	@Before(SaveSchoolValidator.class)
	public void save() {

		UploadFile file = getFile("school.image", Constants.ATTACHMENT_TEMP_PATH,
				Constants.MAX_FILE_SIZE);

		// 保存文件并获取保存在数据库中的路径
		String savePath = UploadKit.saveAvatarImage(file.getFile());

		School school = getModel(School.class);

		System.out.println("savePath: " + savePath);

		// 设置头像路径
		school.set("image", savePath);

		if (null == school.getInt("id")) {
			school.set("uuid", UUID.randomUUID());
			school.save();
		} else {
			school.update();
		}

		redirect("index.html");
	}

	/**
	 * 跳转编辑页面
	 * 
	 */
	public void edit() {
		int schoolId = getParaToInt(0);
		setAttr("school", School.dao.findById(schoolId));
		render("add.html");
	}

	/**
	 * 删除学校信息
	 */
	public void delete() {
		int schoolId = ParaKit.paramToInt(getPara(0), -1);

		if (schoolId > -1) {
			if (School.dao.deleteById(schoolId)) {
				renderJson("msg", "删除成功！");
			}
		} else {
			renderJson("msg", "删除失败！");
		}

	}

}
