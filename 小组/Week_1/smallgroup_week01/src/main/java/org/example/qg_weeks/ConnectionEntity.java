package org.example.qg_weeks;

import java.sql.Connection;

public class ConnectionEntity {
    Connection connection;
    private long useStartTime;//开始使用时间

    public ConnectionEntity(Connection connection, long useStartTime) {
        this.connection = connection;
        this.useStartTime = useStartTime;
    }

    public Connection getConnection() {
        return connection;
    }

    public long getUseStartTime() {
        return useStartTime;
    }


}
