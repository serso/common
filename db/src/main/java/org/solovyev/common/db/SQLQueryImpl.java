package org.solovyev.common.db;

import java.util.HashMap;

/**
 * User: serso
 * Date: Oct 15, 2009
 * Time: 12:22:31 AM
 */
public class SQLQueryImpl implements SQLQuery {

    private HashMap<Enum, String> aliases = new HashMap<Enum, String>();

    private StringBuffer sql = new StringBuffer();

    public void append(String s) {
        sql.append(s);
    }

    public void append(Object o) {
        this.append(o.toString());
    }

    public void append(SQLOperator op) {
        switch (op) {
            case and:
            case from:
            case select:
                append(op.name());
                appendSpace();
                break;
            case comma:
                append(",");
                appendSpace();
                break;
            case where:
                append(op.name());
                appendSpace();
                append("1 = 1");
                appendSpace();
                break;
        }

    }

    public void append(Enum table) {
        append(table.name());
        appendSpace();
        append(this.getAlias(table));
        appendSpace();
    }

    private void appendSpace() {
        append(" ");
    }

    public String getStringQuery() {
        return sql.toString();
    }

    public void append(SQLQuery q) {
        append("(");
        appendSpace();
        append(q.getStringQuery());
        append(")");
        appendSpace();
    }

    public void setTableAlias(Enum table, String alias) {
        aliases.put(table, alias);
    }

    private String getAlias(Enum table) {
        String result = aliases.get(table);
        if (result == null) {
            result = table.name();
        }
        return result;
    }

    public void setConstraint(Enum table1, String column1, String constaint, Enum table2, String column2) {
        append(SQLOperator.and);
        append(table1, column1);
        append(constaint);
        appendSpace();
        append(table2, column2);
    }

    public void append(Enum table, String column) {
        append(getAlias(table));
        append(".");
        append(column);
        appendSpace();
    }

    public void setConstraint(Enum table1, String column1, String constaint, Object value) {
        append(SQLOperator.and);        
        append(table1, column1);
        append(constaint);
        appendSpace();
        append(value);
        appendSpace();
    }
}
