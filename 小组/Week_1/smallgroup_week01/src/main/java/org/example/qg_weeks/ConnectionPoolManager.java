package org.example.qg_weeks;

import java.sql.Connection;

public class ConnectionPoolManager {
    private static loadConfig Config =new loadConfig();
    private static ConnectionPool connectionPool = new ConnectionPool(Config);

    public static  Connection getConnection() {
        return connectionPool.getConnection();
    }

    public static void releaseConnection(Connection connection){
        connectionPool.releaseConnection(connection);
    }

    public static  void closeAllConnection(){

        connectionPool.closeAllConnection();

    }

}
