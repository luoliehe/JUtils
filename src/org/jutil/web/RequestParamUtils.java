package org.jutil.web;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

/**
 * http 请求参数工具类
 * @author luoliehe
 */
public class RequestParamUtils {
	
	/**
	 * Http请求参数映射到实体
	 * @param request
	 * @param c
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	public <T> T smartBinder(HttpServletRequest request,Class<T> c) throws InstantiationException, IllegalAccessException, InvocationTargetException{
		T bean = c.newInstance();
		BeanUtils.populate(bean, request.getParameterMap());
		return bean;
	}
}
