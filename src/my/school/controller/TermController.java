package my.school.controller;

import java.util.List;

import my.school.model.Term;
import my.school.service.TermService;

import com.jfinal.core.Controller;

/**
 * TermController
 * 
 * 学期管理
 * 
 */
public class TermController extends Controller {

	public void index() {

		List<Term> terms = Term.dao.getTerms();

		setAttr("terms", terms);

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
