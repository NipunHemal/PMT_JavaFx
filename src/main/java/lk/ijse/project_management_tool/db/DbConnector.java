package lk.ijse.project_management_tool.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    private static DbConnector dbConnector;
    private final Connection connection;

    private DbConnector() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_management_tool","root","1234");
    }

    public static DbConnector getInstance() throws ClassNotFoundException, SQLException {
        if (dbConnector == null) {
            dbConnector = new DbConnector();
        }
        return dbConnector;
    }

    public Connection getConnection() {
        return connection;
    }
}