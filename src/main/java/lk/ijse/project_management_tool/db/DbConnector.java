package lk.ijse.project_management_tool.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    private static DbConnector dbConnector;
    private final Connection connection;

//    private static final String URL = "jdbc:postgresql://ep-tight-leaf-a4lvxt4l-pooler.us-east-1.aws.neon.tech/pmt";
//    private static final String USER = "pmt_owner";
//    private static final String PASSWORD = "npg_QmGHBFsb7n9v";

    private DbConnector() throws ClassNotFoundException, SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://95.111.248.142:6300/pmt", "root", "1234");
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