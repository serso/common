package org.solovyev.common.model.producers;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * User: serso
 * Date: Oct 16, 2009
 * Time: 12:34:16 AM
 */
public interface SQLCommand<T> {
    T execute(Connection dbConnection) throws SQLException;
}
