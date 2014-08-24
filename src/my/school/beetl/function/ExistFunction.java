package my.school.beetl.function;

import org.bee.tl.core.Context;
import org.bee.tl.core.Function;

public class ExistFunction implements Function{

	@Override
	public Object call(Object[] params, Context arg1) {
		
		if (params.length != 2){
            throw new RuntimeException("length of params must be 2 !");
        }
		
        if (params[0] != null){
            return params[1].toString();
        }
        
        return null;
	}

}
