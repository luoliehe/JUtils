package org.victor.utils.jdbc;

import java.util.ArrayList;
import java.util.List;

public class SqlBuilder {
    private StringBuilder sqlBuilder = new StringBuilder();
    private List<Object> values = new ArrayList<>();

    public SqlBuilder appendSql(String s) {
        sqlBuilder.append(s);
        return this;
    }    

    public SqlBuilder appendValue(Object value) {
        sqlBuilder.append('?');
        values.add(value);
        return this;
    }

    public SqlBuilder appendValues(Object[] values) {
        sqlBuilder.append('(').append(values[0]);
        for (int i = 1, c = values.length; i < c; ++i) {
            sqlBuilder.append('?').append(','); 
            this.values.add(values[i]);
        }
        sqlBuilder.append(")  ");
        return this;
    }

    public String getSql() {
        return sqlBuilder.toString();
    }

    public Object[] getValues() {
        return values.toArray();
    }

    public boolean hasValue() {
        return ! values.isEmpty();
    }
    
    public String getCountSql(){
    	return "select count(1) from "+ sqlBuilder.toString().split("from", 1);
    }
}