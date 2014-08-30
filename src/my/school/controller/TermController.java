package my.school.controller;

import my.school.config.Constants;
import my.school.kit.ParaKit;
import my.school.model.Term;
import my.school.service.TermService;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

/**
 * TermController
 * 
 * 学期管理
 * 
 */
public class TermController extends Controller {

	public void index() {

		int page = ParaKit.paramToInt(getPara("p"), 1);

		if (page < 1) {
			page = 1;
		}

		// 读取所有的学期信息
		Page<Term> termList = Term.dao.paginate(page, Constants.PAGE_SIZE);

		setAttr("termList", termList);

		render("index.html");
	}

	/**
	 * 生成一个学期
	 */
	public void create() {

		boolean result = TermService.createTerm();

		if (result) {
			setAttr("status", "success");
		} else {
			setAttr("status", "error");
		}

		renderJson();
	}

}
