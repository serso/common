package org.solovyev.common.model.producers;

import org.apache.log4j.Logger;

import java.util.Properties;
import java.util.List;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * User: serso
 * Date: Oct 18, 2009
 * Time: 11:44:04 PM
 */
public class ConnectionManager {

    private final static int MAX_NUMBER_OF_CONNECTIONS = 10;
    private final static Connection[] connections = new Connection[MAX_NUMBER_OF_CONNECTIONS];
    private final static List<Connection> unclosedConnections = new ArrayList<Connection>();
    private static int numberOfFreeConnections = MAX_NUMBER_OF_CONNECTIONS;

    static {
        for (int i = 0; i < MAX_NUMBER_OF_CONNECTIONS; i++) {
            connections[i] = createConnection();
        }
    }

    synchronized public static Connection getConnection() {
        Connection connection = null;
        while (numberOfFreeConnections == 0) {
            try {
                ConnectionManager.class.wait();
            } catch (InterruptedException e) {
                Logger.getLogger(ConnectionManager.class).error(e.getMessage(), e);
            }
        }
        connection = getFreeConnection(connection);
        return connection;
    }

    synchronized private static void addConnection() {
        for (int i = 0; i < MAX_NUMBER_OF_CONNECTIONS; i++) {
            if (connections[i] == null) {
                connections[i] = createConnection();
                break;
            }
        }
    }

    private static Connection createConnection() {
        Connection result = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Properties props = new Properties();
            props.setProperty("user", "user");
            props.setProperty("password", " password");
            props.setProperty("useUnicode", "true");
            props.setProperty("characterEncoding", "windows-1251");
            result = DriverManager.getConnection(
                    "jdbc:mysql://localhost/Public", props);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static void closeConnection(Connection c) {
        if (c != null) {
            try {
                c.rollback();
                setFreeConnection(c);
            } catch (SQLException e) {
                Logger.getLogger(ConnectionManager.class).error(e.getMessage(), e);
                try {
                    c.close();
                    addConnection();
                } catch (SQLException e1) {
                    Logger.getLogger(ConnectionManager.class).error(e1.getMessage(), e1);
                    unclosedConnections.add(c);
                }
            }
        }
    }

    public static List<Connection> getUnclosedConnections() {
        return unclosedConnections;
    }

    private static Connection getFreeConnection(Connection connection) {
        numberOfFreeConnections--;
        for (int i = 0; i < MAX_NUMBER_OF_CONNECTIONS; i++) {
            if (connections[i] != null) {
                connection = connections[i];
                connections[i] = null;
                break;
            }
        }
        ConnectionManager.class.notify();
        return connection;
    }

    private static void setFreeConnection(Connection c) {
        numberOfFreeConnections++;
        for (int i = 0; i < MAX_NUMBER_OF_CONNECTIONS; i++) {
            if (connections[i] == null) {
                connections[i] = c;
                break;
            }
        }
        ConnectionManager.class.notify();
    }
}