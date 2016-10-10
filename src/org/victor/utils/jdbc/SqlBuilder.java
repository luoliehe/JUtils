package org.victor.utils.jdbc;

import java.util.ArrayList;
import java.util.List;

public class SqlBuilder {

	private StringBuilder sqlBuf = new StringBuilder();
	private List<Object> values = new ArrayList<>();

	public SqlBuilder appendSql(String sql) {
		sqlBuf.append(sql);
		return this;
	}

	public SqlBuilder appendValue(Object value) {
		sqlBuf.append('?');
		values.add(value);
		return this;
	}

	public SqlBuilder appendValues(Object[] values) {
		sqlBuf.append('(');
		for (int i = 0, c = values.length; i < c; ++i) {
			sqlBuf.append('?').append(',');
			this.values.add(values[i]);
		}
		int last = sqlBuf.length() - 1;
		if (last > 0 && sqlBuf.charAt(last) == ',') {
			sqlBuf.setCharAt(last, ')');
		}

		return this;
	}

	public String getSql() {
		return sqlBuf.toString();
	}

	public Object[] getValues() {
		return values.toArray();
	}

	public boolean hasValue() {
		return !values.isEmpty();
	}
}