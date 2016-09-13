package org.jutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期辅助工具类
 * 
 * @author victor.luo
 */
public abstract class DateUtils extends org.apache.commons.lang3.time.DateUtils{
	
	/**yyyy-MM-dd*/
	private static final String TODAY_PATTERN = "yyyy-MM-dd";
	
	/**yyyy-MM-dd hh:mm:ss*/
	private static final String SIMPLE_PATTERN = "yyyy-MM-dd hh:mm:ss";
	
	public static Date getNowTime() {
		return new Date();
	}

	public static long getTimeMillis() {
		return System.currentTimeMillis();
	}

	public static String format(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}
	
	/**
	 * 简单 format成 {@value #SIMPLE_PATTERN}
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date,SIMPLE_PATTERN);
	}
	
	public static Date parse(String date, String pattern) 
			throws ParseException {
		return parseDate(date, pattern);
	}
	
	public static Date parse(String date) 
			throws ParseException {
		return parseDate(date, SIMPLE_PATTERN);
	}
	
	public static boolean isToday(Date date) {
		return isSameDay(getNowTime(), date);
	}
	
	public static boolean isYesterday(Date date){
		return equalsDay(getYesterday(), date);
	}
	
	public static boolean isTomorrow(Date date){
		return equalsDay(getTomorrow(), date);
	}
	
	/**
	 * 年月日是否相等
	 * @param src
	 * @param target
	 * @return
	 */
	public static boolean equalsDay(Date src, Date target){
		if(src != null && target != null ){
			return false;
		}
		return format(src, TODAY_PATTERN).equals(format(target, TODAY_PATTERN));
	}
	
	/**
	 * 获得今天凌晨0点时间
	 * @return 返回格式 yyyy/MM/dd 00:00:00
	 */
	public static Date getToday() {
		return truncate(getNowTime(), Calendar.DATE);
	}
	
	/**
	 * 获得昨天凌晨0点时间
	 * @return 返回格式 yyyy/MM/dd 00:00:00
	 */
	public static Date getYesterday(){
		return addDays(getToday(), -1);
	}
	
	/**
	 * 获得昨天凌晨0点时间
	 * @return 返回格式 yyyy/MM/dd 00:00:00
	 */
	public static Date getTomorrow(){
		Calendar c = Calendar.getInstance();
		c.setTime(getToday());
		c.add(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}
	
	public static int getSecond(){
		return getSecond(getNowTime());
	}
	
	public static int getSecond(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.SECOND);
	}
	
	public static int getMinute(){
		return getMinute(getNowTime());
	}
	
	/**
	 * 返回当前的分钟数
	 * @param date
	 * @return 返回值范围 [0,59]
	 */
	public static int getMinute(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}
	
	public static int getHours(){
		return getHours(getNowTime());
	}
	
	public static int getHours(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * @param date
	 * @return 范围 [1~12]
	 */
	public static int getMonth(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH)+1;
	}
	
	/**
	 * @param date
	 * @return 范围 [1~12]
	 */
	public static int getMonth(){
		return getMonth(getNowTime());
	}
	
	public static int getDay(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DATE);
	}
	
	public static int getDay(){
		return getDay(getNowTime());
	}
	
	public static int getYear(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}
	
	public static int getYear(){
		return getYear(getNowTime());
	}
	
	/**
	 * 返回当前向前，或向后天列表，顺序都是从小到大顺序 
	 * @param from
	 * @param days
	 * @return
	 */
	public static List<Date> getDateArray(Date from, int days) {
		int b = days < 0 ? days + 1 : 0;
		int e = days < 0 ? 1 : days;
		List<Date> list = new ArrayList<>();
		for (int i = b; i < e; i++) {
			list.add(addDays(from, i));
		}
		return list;
	}
	
	public static void main(String[] args) throws ParseException {
		System.out.println(getDateArray(new Date(), 3));
		System.out.println(getDateArray(new Date(), -3));
		
		System.out.println(String.format("getNowTime = %s",getNowTime()));
		System.out.println(String.format("getToday = %s",getToday()));
		System.out.println(String.format("getYesterday = %s",getYesterday()));
		System.out.println(String.format("getTomorrow = %s",getTomorrow()));
		System.out.println(String.format("getYesterday = %s",getYesterday()));
		System.out.println(String.format("getYear = %s",getYear()));
		System.out.println(String.format("getMonth = %s",getMonth()));
		System.out.println(String.format("getDay = %s",getDay()));
		System.out.println(String.format("getHours = %s",getHours()));
		System.out.println(String.format("getMinute = %s",getMinute()));
		System.out.println(String.format("getSecond = %s",getSecond()));
	}
}
