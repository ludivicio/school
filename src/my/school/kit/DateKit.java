package my.school.kit;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateKit {

	public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 获取今天的日期
	 * 
	 * @return
	 */
	public static String getToday() {
		return formatter.format(new Date().getTime());
	}

	/**
	 * 获取格式化的日期
	 * 
	 * @param time
	 * @return
	 */
	public static String format(long time) {
		return formatter.format(time);
	}
	
	/**
	 * 获取格式化的日期
	 * 
	 * @param time
	 * @return
	 */
	public static String format(String format, String time) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(Long.parseLong(time));
	}

}
