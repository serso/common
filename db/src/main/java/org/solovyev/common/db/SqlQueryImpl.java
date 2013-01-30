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

import java.util.HashMap;
import java.util.Map;

/**
 * User: serso
 * Date: Oct 15, 2009
 * Time: 12:22:31 AM
 */
public class SqlQueryImpl implements SqlQuery {

    @NotNull
    private final Map<Enum, String> aliases = new HashMap<Enum, String>();

    private final StringBuilder sql = new StringBuilder();

    public void append(String s) {
        sql.append(s);
    }

    public void append(Object o) {
        this.append(o.toString());
    }

    public void append(SqlOperator op) {
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

    public String toString() {
        return sql.toString();
    }

    public void append(SqlQuery q) {
        append("(");
        appendSpace();
        append(q.toString());
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

    public void setConstraint(Enum table1, String column1, String constraint, Enum table2, String column2) {
        append(SqlOperator.and);
        append(table1, column1);
        append(constraint);
        appendSpace();
        append(table2, column2);
    }

    public void append(Enum table, String column) {
        append(getAlias(table));
        append(".");
        append(column);
        appendSpace();
    }

    public void setConstraint(Enum table1, String column1, String constraint, Object value) {
        append(SqlOperator.and);
        append(table1, column1);
        append(constraint);
        appendSpace();
        append(value);
        appendSpace();
    }
}
