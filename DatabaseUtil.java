package healthcare;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://database-cse360.c70wguu4oz17.us-east-2.rds.amazonaws.com:3306/CSE360";
    private static final String USER = "admin"; // Your RDS username
    private static final String PASSWORD = "cse360pass"; // Your RDS password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
