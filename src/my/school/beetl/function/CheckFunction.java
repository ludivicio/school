package my.school.beetl.function;

import my.school.kit.ParaKit;

import org.bee.tl.core.Context;
import org.bee.tl.core.Function;

/**
 * 如果第一个参数和第二个参数的值相等，则返回第三个参数的值
 */
public class CheckFunction implements Function {
	public String call(Object[] params, Context ctx) {

		if (params.length != 3) {
			throw new RuntimeException("length of params must be 3 !");
		}

		if (params[0] != null && params[1] != null) {
			String one = params[0].toString();
			String two = params[1].toString();

			if (!ParaKit.isEmpty(one) && !ParaKit.isEmpty(two)) {

				if (one.equals(two)) {
					return params[2].toString();
				}
			}
		}

		return null;
	}
}
