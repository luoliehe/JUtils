package org.victor.utils.sql;

public enum Condition {

	eq(" = ", " 等于 "), 
	nq(" != ", " 不等于 "), 
	gt(" > ", " 大于 "), 
	ge(" >= ", " 大于等于 "), 
	lt(" < ", " 小于 "), 
	le(" <= ", " 小于等于 "),
	nl(" is null ", " 为空 "), 
	nn(" is not null ", " 不为空 "), 
	cn(" like ", " 包含 "), 
	sw(" like ", " 开头为 "), 
	ew(" like ", " 结尾为 "), 
	in(" in ", " 在...里面 "), 
	ni(" not in ", " 不在...里面 ");
	
	public final String condition;
	public final String desc;

	private Condition(String c, String desc) {
		this.condition = c;
		this.desc = desc;
	}
	
}
