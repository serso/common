package org.solovyev.common.model.producers;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 * User: serso
 * Date: Oct 15, 2009
 * Time: 12:14:21 AM
 */
public interface Producer<T>{
    public T createInstance(ResultSet rs, Connection dbConnection) throws SQLException;
}
