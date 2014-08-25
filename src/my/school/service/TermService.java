package my.school.service;

import java.text.ParseException;

import my.school.kit.DateKit;
import my.school.model.Term;

public class TermService {

	private static final int FIRST_TERM_START_MONTH = 9;
	private static final int SECOND_TERM_START_MONTH = 2;

	public static boolean createTerm() {
		// 从数据库中查找最后的一条记录，根据最后一条记录来获取时间，并生成学期

		Term term = Term.dao.getLastTerm();

		boolean result = false;

		if (term == null) {

			System.out.println("term is null");

			// 说明数据库中尚无数据
			// 生成今年的学期数据并保存到数据库
			int year = DateKit.getYear();
			int month = DateKit.getMonth();

			term = createTerm(year, month);

			result = term.save();

		} else {

			String end = term.getStr("end");

			System.out.println("name: " + term.getStr("name"));

			try {

				int year = DateKit.getYear("yyyy年M月", end);
				int month = DateKit.getMonth("yyyy年M月", end);

				System.out.println("year = " + year);
				System.out.println("month = " + month);

				if (month < FIRST_TERM_START_MONTH && month >= SECOND_TERM_START_MONTH ) {
					month = FIRST_TERM_START_MONTH;
				} else if (month >= FIRST_TERM_START_MONTH) {
					year += 1;
					month = SECOND_TERM_START_MONTH;
				} else if (month < SECOND_TERM_START_MONTH) {
					month = SECOND_TERM_START_MONTH;
				}

				term = createTerm(year, month);
				
				result = term.save();

			} catch (ParseException e) {
				result = false;
				e.printStackTrace();
			}

		}

		return result;
	}

	/**
	 * 根据年月创建学期
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	private static Term createTerm(int year, int month) {

		String name = null;
		String start = null;
		String end = null;

		// 月份大于8月的话，算作第一学期
		// 月份小于2月的话，算作去年的第一学期
		if (month >= FIRST_TERM_START_MONTH) {

			name = year + "-" + (year + 1) + "学年第1学期";
			start = year + "年" + FIRST_TERM_START_MONTH + "月";
			end = (year + 1) + "年" + (SECOND_TERM_START_MONTH - 1) + "月";

		} else if (month < SECOND_TERM_START_MONTH) {

			name = (year - 1) + "-" + year + "学年第1学期";
			start = (year - 1) + "年" + FIRST_TERM_START_MONTH + "月";
			end = year + "年" + (SECOND_TERM_START_MONTH - 1) + "月";

		} else if (month >= SECOND_TERM_START_MONTH && month < FIRST_TERM_START_MONTH) {

			name = (year - 1) + "-" + year + "学年第2学期";
			start = year + "年" + SECOND_TERM_START_MONTH + "月";
			end = year + "年" + (FIRST_TERM_START_MONTH - 2) + "月";

		}

		Term term = new Term();

		term.set("name", name);
		term.set("start", start);
		term.set("end", end);

		return term;
	}

}
