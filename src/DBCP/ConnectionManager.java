package DBCP;

import java.sql.*;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class ConnectionManager {

    private static BasicDataSource ds = null;

// DBCP를 생성한다.
    private void setupDataSource() {
        if (ds == null) {
            ds = new BasicDataSource();
            String url = "jdbc:mysql://localhost:3306/pos";
            String className = "com.mysql.jdbc.Driver";
            String userName = "root";
            String passWord = "root";
            ds.setDriverClassName(className);
            ds.setUrl(url);
            ds.setUsername(userName);
            ds.setPassword(passWord);
            ds.setMaxActive(100);
            ds.setInitialSize(10);
            ds.setMinIdle(5);
            ds.setMaxWait(20);
            ds.setPoolPreparedStatements(true);
        }
    }

// BasicDataSource로부터 connection을 얻어온다.

    public Connection getConnection() throws SQLException {
        setupDataSource();
        return ds.getConnection();
    }
}
