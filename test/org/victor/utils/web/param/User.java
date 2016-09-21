package org.victor.utils.web.param;

import java.util.Arrays;
import java.util.Date;

public class User {
	private int age;
	private Date birthday;
	private String name;
	private double height;
	private boolean married;
	private int a[];

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public boolean isMarried() {
		return married;
	}

	public void setMarried(boolean married) {
		this.married = married;
	}

	public int[] getA() {
		return a;
	}

	public void setA(int[] a) {
		this.a = a;
	}

	@Override
	public String toString() {
		return "User [age=" + age + ", birthday=" + birthday + ", name=" + name
				+ ", height=" + height + ", married=" + married + ", a="
				+ Arrays.toString(a) + "]";
	}

 
}
