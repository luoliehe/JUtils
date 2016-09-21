package org.victor.utils.web.param;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;


public class RequestParamUtilsTest extends RequestParamUtils{
	
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
		User user = new User();
		
		Map<String, Object> map = new HashMap<>();
		map.put("married", "true");
		map.put("age", "10");
		map.put("birthday", "1992/12/12");
//		map.put("birthday", new Date());
		map.put("height", "175.3");
		map.put("a", "'1','2',2");
		binder(user, map);
		
		System.out.println(user);
		
	}
}
