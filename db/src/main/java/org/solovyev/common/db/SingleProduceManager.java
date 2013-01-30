/*
 * Copyright 2013 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ---------------------------------------------------------------------
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

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
public class SingleProduceManager<T> extends AbstractProduceManager<T> implements SqlCommand<T> {

    public SingleProduceManager(SqlProducer<T> producer) {
        super(producer);
    }

    public T execute(Connection dbConnection) throws SQLException {
        Statement s = dbConnection.createStatement();
        ResultSet rs;
        T result = null;
        rs = s.executeQuery(producer.getQuery().toString());
        if (rs != null && rs.next()) {
            result = producer.createInstance(rs, dbConnection);
        }
        return result;
    }
}
