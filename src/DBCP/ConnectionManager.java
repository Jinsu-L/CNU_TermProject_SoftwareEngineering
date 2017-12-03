package DBCP;

import java.sql.*;

import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionManager {

    private BasicDataSource ds = null;

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

            ds.setMaxTotal(10);

            ds.setInitialSize(10);

            ds.setMinIdle(5);

            ds.setMaxWaitMillis(5000);

            ds.setPoolPreparedStatements(true);

        }

    }

// BasicDataSource로부터 connection을 얻어온다.

    public Connection getConnection() throws SQLException {

        setupDataSource();

        return ds.getConnection();

    }

}
