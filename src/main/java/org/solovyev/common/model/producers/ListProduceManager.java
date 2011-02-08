package org.solovyev.common.model.producers;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * User: serso
 * Date: Oct 16, 2009
 * Time: 12:46:54 AM
 */
public class ListProduceManager<T> extends AbstractProduceManager<T> implements SQLCommand<List<T>>{

    private List<T> list;

    public ListProduceManager(SQLProducer<T> tsqlProducer, List<T> list) {
        super(tsqlProducer);
        this.list = list;
    }

    public ListProduceManager(SQLProducer<T> tsqlProducer) {
        this(tsqlProducer, new ArrayList<T>());
    }

    public List<T> execute(Connection dbConnection) throws SQLException {
        Statement s = dbConnection.createStatement();
        ResultSet rs;
        rs = s.executeQuery(producer.getQuery().getStringQuery());
        if (rs != null ) {
            while (rs.next()) {
                list.add(producer.createInstance(rs, dbConnection));
            }
        }
        return list;
    }
}
