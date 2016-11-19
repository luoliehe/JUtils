package org.victor.utils.web.param;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * http 请求参数工具类,将参数自动映射到实体中，根据名称匹配
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

	/**
	 * support convert sring to type:
	 * <br>
	 * {@link java.util.Date}<br>
	 * {@link java.sql.Date}<br>
	 * {@link java.sql.Timestamp}<br>
	 * the string must pattern normal format:<br>
	 * <em>yyyy-MM-dd,  yyyy-MM-dd HH:mm:ss, yyyy/MM/dd, yyyy/MM/dd HH:mm:ss
	 * 
	 * @author llh
	 */
	private final static class DateConverter implements Converter {
		
		private final Set<String> patterns = new HashSet<>();
		{
			patterns.add("yyyy-MM-dd");
			patterns.add("yyyy-MM-dd HH:mm:ss");
			patterns.add("yyyy/MM/dd");
			patterns.add("yyyy/MM/dd HH:mm:ss");
		}
		
	    public <T> T convert(Class<T> type, Object value){
	    	
	    	if(value instanceof Date || value instanceof java.sql.Date || value instanceof Timestamp){
	    		return type.cast(value);
	    	}
	    	
	    	if(value instanceof String){
	    		if(StringUtils.isNotBlank((String)value)){
					try {
						//尝试转换
						Date date = DateUtils.parseDate((String)value, patterns.toArray(new String[0]));
						T res = null;
						if(type == Timestamp.class){
							res = type.cast(new Timestamp(date.getTime()));
				        }else if(type == Date.class){  
				            res = type.cast(new Date(date.getTime()));
				        }else if(type == java.sql.Date.class){
				        	res = type.cast(new java.sql.Date(date.getTime()));
				        }
						return res;
					} catch (ParseException e) {
						throw new ConversionException(e);
					}
	    		}
	    	}
	        throw new ConversionException("不能转换 " + value.getClass().getName() + " 为 " + type.getName());  
	    }  
	}
}
