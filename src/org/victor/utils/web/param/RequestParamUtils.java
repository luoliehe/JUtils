package org.victor.utils.web.param;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.victor.utils.web.param.convert.DateConverter;

/**
 * http 请求参数工具类
 * 
 * @author luoliehe
 */
public class RequestParamUtils {

	/**
	 * 将request参数映射到实体
	 * @param request
	 * @param c
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static <T> T binder(Class<T> c, HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException,
			InstantiationException {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = request.getParameterMap();
		
		return (T) binder(c.newInstance(), map);
	}

	/**
	 * 将 map键值对绑定到实体
	 * @param bean
	 * @param properties
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static <T> T binder(T bean, Map<String, ? extends Object> properties)
			throws IllegalAccessException, InvocationTargetException {
		
		ConvertUtils.register(new DateConverter(), java.util.Date.class);
		ConvertUtils.register(new DateConverter(), java.sql.Date.class);
		ConvertUtils.register(new DateConverter(), java.sql.Timestamp.class);
		BeanUtils.populate(bean, properties);
		
		return bean;
	}

}
