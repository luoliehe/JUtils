package org.victor.utils.sql;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.victor.utils.jdbc.SqlBuilder;

public class Request2Sql {

	public static SqlBuilder toSql(String tableName, Map<String, String[]> map) {
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" select * from ").appendSql(tableName);
		sql.appendSql(" where 1=1 ");

		for (String key : map.keySet()) {
			for (Condition c : Condition.values()) {
				if (!key.startsWith(c.name() + "_")) {
					continue;
				}

				sql.appendSql(" and " + key.substring(3) + " " + c.condition);
				String[] values = map.get(key);

				switch (c) {
				case cn:
					sql.appendValue("%"+ values[0] +"%");
					break;
				case in:
				case ni:
					sql.appendValues(values);
					break;
				default:
					sql.appendValue(values[0]);
					break;
				}

			}
		}
		return sql;
	}

	public static void main(String[] args) {
		Map<String, String[]> map = new HashMap<>();
		map.put("eq_age", Arrays.asList("1").toArray(new String[0]));
		map.put("cn_name", Arrays.asList("张三").toArray(new String[0]));
		map.put("in_in", new String[]{"111","222"});
		// map.put("ni_ni", Arrays.asList("1","2").toArray(new String[0]));
		SqlBuilder sql = toSql("user", map);
		System.out.println(sql.getSql());
		System.out.println(Arrays.asList(sql.getValues()));
	}
}
