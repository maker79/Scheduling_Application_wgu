package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    // JDBC URL parts
    private static final String PROTOCOL = "jdbc";
    private static final String VENDOR_NAME = ":mysql:";
    private static final String IP_ADDRESS = "//3.227.166.251/U04R8N";
    // JDBC URL
    private static final String JDBC_URL = PROTOCOL + VENDOR_NAME + IP_ADDRESS;
    // Driver interface reference
    private static final String MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static Connection connection;
    // username and password
    private static final String USER_NAME = "U04R8N";
    private static final String PASSWORD = "53688320548";

    public static void makeConnection() throws ClassNotFoundException, SQLException {
//        Class.forName(MYSQL_JDBC_DRIVER);
        connection = (Connection) DriverManager.getConnection(JDBC_URL, USER_NAME, PASSWORD);
        System.out.println("Connection successful...");
    }

    public static void closeConnection() throws SQLException {
        connection.close();
        System.out.println("Connection closed successfully...");
    }

}
