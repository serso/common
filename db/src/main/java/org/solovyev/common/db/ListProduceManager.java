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

import org.jetbrains.annotations.NotNull;

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
public class ListProduceManager<T> extends AbstractProduceManager<T> implements SqlCommand<List<T>> {

    @NotNull
    private final List<T> list;

    public ListProduceManager(@NotNull SqlProducer<T> sqlProducer,
                              @NotNull List<T> list) {
        super(sqlProducer);
        this.list = list;
    }

    public ListProduceManager(SqlProducer<T> sqlProducer) {
        this(sqlProducer, new ArrayList<T>());
    }

    public List<T> execute(Connection dbConnection) throws SQLException {
        Statement s = dbConnection.createStatement();
        ResultSet rs;
        rs = s.executeQuery(producer.getQuery().toString());
        if (rs != null ) {
            while (rs.next()) {
                list.add(producer.createInstance(rs, dbConnection));
            }
        }
        return list;
    }
}
