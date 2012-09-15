package org.solovyev.common.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * User: serso
 * Date: Oct 15, 2009
 * Time: 12:13:28 AM
 */
public class SingleProduceManager<T> extends AbstractProduceManager<T> implements SQLCommand<T>{

    public SingleProduceManager(SQLProducer<T> producer) {
        super(producer);
    }

    public T execute(Connection dbConnection) throws SQLException {
        Statement s = dbConnection.createStatement();
        ResultSet rs;
        T result = null;
        rs = s.executeQuery(producer.getQuery().getStringQuery());
        if (rs != null && rs.next()) {
            result = producer.createInstance(rs, dbConnection);
        }
        return result;
    }
}
