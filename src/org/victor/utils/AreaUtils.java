package org.victor.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 地区格式化工具
 * 将地区按指定格式化输出，例如：广东广州  > 广东省广州市
 * @author Victor
 */
public final class AreaUtils {

	private AreaUtils() {
	}

	// 直辖市
	final static Map<String, String> municipality = new HashMap<>();
	static {
		// 直辖市
		municipality.put("北京", "北京");
		municipality.put("上海", "上海");
		municipality.put("天津", "天津");
		municipality.put("重庆", "重庆");
		// 特别行政区
		municipality.put("香港", "香港");
		municipality.put("澳门", "香港");
	}

	final static Map<String, String> province = new HashMap<>();
	static {
		// 省
		province.put("^广东省?", "广东省");
		province.put("^山东省?", "山东省");
		province.put("^江苏省?", "江苏省");
		province.put("^河南省?", "河南省");
		province.put("^河北省?", "河北省");
		province.put("^浙江省?", "浙江省");
		province.put("^陕西省?", "陕西省");
		province.put("^湖南省?", "湖南省");
		province.put("^福建省?", "福建省");
		province.put("^云南省?", "云南省");
		province.put("^四川省?", "四川省");
		province.put("^安徽省?", "安徽省");
		province.put("^海南省?", "海南省");
		province.put("^江西省?", "江西省");
		province.put("^湖北省?", "湖北省");
		province.put("^山西省?", "山西省");
		province.put("^辽宁省?", "辽宁省");
		province.put("^台湾省?", "台湾省");
		province.put("^贵州省?", "贵州省");
		province.put("^甘肃省?", "甘肃省");
		province.put("^青海省?", "青海省");
		province.put("^吉林省?", "吉林省");
		province.put("^黑龙江省?", "黑龙江省");
		// 自治区
		province.put("^广西(省)?(壮族)?(自治区)?", "广西");
		province.put("^内蒙(古)?(自治区)?", "内蒙古");
		province.put("^新疆(省)?(维吾尔)?(自治区)?", "新疆");
		province.put("^西藏(省)?(区)?(自治区)?", "西藏");
		province.put("^宁夏(省)?(回族)?(自治区)?", "宁夏");
	}

	/**
	 * @param province
	 * @param city
	 * @return
	 * @throws IllegalArgumentException
	 *             格式化错误异常
	 */
	public static AreaAddress format(String province, String city) throws IllegalArgumentException {
		return format(province + city);
	}

	/**
	 * 格式化地区
	 * 
	 * @param area
	 * @return
	 * @throws IllegalArgumentException
	 *             格式化错误异常
	 */
	public static AreaAddress format(final String area) throws IllegalArgumentException {
		if (StringUtils.isBlank(area)) {
			throw new IllegalArgumentException("格式化的地区不能为空");
		}
		String areaName = area.replaceAll("\\s*", "");

		for (String regex : municipality.keySet()) {
			if (area.contains(regex)) {
				return new AreaAddress(municipality.get(regex), municipality.get(regex));
			}
		}

		for (String regex : province.keySet()) {
			if (matcher(regex, areaName)) {
				// TODO 市级别，太多不再校验
				String city = areaName.replaceAll(regex, "");
				return new AreaAddress(province.get(regex), city = city.endsWith("市") ? city : city + "市");
			}
		}
		throw new IllegalArgumentException("地区不明确并无法格式化，" + areaName);
	}

	private static boolean matcher(String regex, String area) {
		Pattern p = Pattern.compile(regex);
		return p.matcher(area).find();
	}

	public final static class AreaAddress {
		String province;
		String city;

		public AreaAddress() {
		}

		public AreaAddress(String province, String city) {
			this.province = province;
			this.city = city;
		}

		public String getProvince() {
			return province;
		}

		public String getCity() {
			return city;
		}

		@Override
		public String toString() {
			return StringUtils.equals(province, city) ? province : province + city;
		}
	}

	public static void main(String[] args) {

		System.out.println(format("河北省北京"));
		System.out.println(format("河北省北京市"));

		// 正常的城市
		System.out.println(format(" 广   东"));
		System.out.println(format("广东省"));
		System.out.println(format("广东广州"));
		System.out.println(format("广东    广州市"));
		System.out.println(format("广东省广州"));
		System.out.println(format("广东省广州市"));

		System.out.println(format("广东省香港行政"));
		System.out.println(format("香港行政"));
		System.out.println(format("香港行政区"));
		System.out.println(format("香港特别行政"));
		System.out.println(format(" 香港特别行政  区 "));

		System.out.println(format("内蒙巴彦淖尔市"));
		System.out.println(format("内蒙古巴彦淖尔市"));
		System.out.println(format("内蒙古巴彦淖尔市"));
		System.out.println(format("内蒙古自治区巴彦淖尔市"));
		System.out.println(format("内蒙自治区巴彦淖尔市"));

		System.out.println(format("广西玉林市"));
		System.out.println(format("广西玉林"));
		System.out.println(format("广西省玉林市"));
		System.out.println(format("广西省玉林"));
		System.out.println(format("广西省壮族玉林"));
		System.out.println(format("广西省壮族自治区玉林"));
		System.out.println(format("广西省自治区玉林"));
		System.out.println(format("广西自治区玉林"));

		System.out.println(format("内蒙古兴安盟"));
		System.out.println(format("内蒙古通辽市"));
		System.out.println(format("内蒙古呼和浩特市"));

	}
}
