package org.solovyev.common.db;

/**
 * User: serso
 * Date: Oct 16, 2009
 * Time: 12:37:57 AM
 */
public abstract class AbstractSQLProducer<T> implements SQLProducer<T> {

    protected SQLQuery query = new SQLQueryImpl();

    public SQLQuery getQuery() {
        return query;
    }
}
