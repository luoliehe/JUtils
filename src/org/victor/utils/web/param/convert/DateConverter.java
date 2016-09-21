package org.victor.utils.web.param.convert;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

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
public class DateConverter implements Converter{ 
	
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