package org.solovyev.common.db;

/**
 * User: serso
 * Date: Oct 15, 2009
 * Time: 12:19:10 AM
 */
public interface SQLQuery {

    public void append(String s);

    public void append(Object o);

    public void append(SQLOperator op);

    public void append(Enum table);

    public String getStringQuery();

    public void append(SQLQuery q);

    public void setTableAlias( Enum table, String alias );

    public void setConstraint(Enum table1, String column1, String constaint, Enum table2, String column2);

    public void setConstraint(Enum table1, String column1, String constaint, Object value);

    public void append(Enum table, String column);

}
