package lk.ijse.project_management_tool.utils;

import lk.ijse.project_management_tool.db.DbConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {
    public static <T> T execute(String sql, Object... obj) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnector.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement(sql);

        // Bind parameters
        for (int i = 0; i < obj.length; i++) {
            pst.setObject(i + 1, obj[i]);
        }

        // Print SQL with substituted values (simulated)
        System.out.println("Executing SQL: " + formatSqlWithParams(sql, obj));

        // Run query or update
        if (sql.trim().toLowerCase().startsWith("select")) {
            ResultSet resultSet = pst.executeQuery();
            return (T) resultSet;
        } else {
            int affectedRows = pst.executeUpdate();
            return (T) (Boolean) (affectedRows > 0);
        }
    }

    // Helper to simulate substituted SQL (for logging/debugging only)
    private static String formatSqlWithParams(String sql, Object... params) {
        for (Object param : params) {
            String value;
            if (param == null) {
                value = "NULL";
            } else if (param instanceof String || param instanceof java.util.Date) {
                value = "'" + param.toString().replace("'", "''") + "'";
            } else {
                value = param.toString();
            }
            sql = sql.replaceFirst("\\?", value);
        }
        return sql;
    }
}
