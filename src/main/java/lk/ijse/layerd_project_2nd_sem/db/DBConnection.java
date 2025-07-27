package lk.ijse.layerd_project_2nd_sem.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;
    private final Connection connection;

    private DBConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/alphamodificationstore", "root", "mysql");
    }
    public static DBConnection getDbConnection() throws SQLException, ClassNotFoundException {
        return dbConnection == null ? dbConnection = new DBConnection() : dbConnection;
    }
    public Connection getConnection() {
        return connection;
    }
}
