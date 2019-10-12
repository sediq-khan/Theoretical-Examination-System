package dbUtil;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
    public static Connection getConnection(String dbName, String dbUsername, String dbPassword) throws SQLException{
       try {
           Class.forName("com.mysql.cj.jdbc.Driver");
           return DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName, dbUsername, dbPassword);
       }catch (ClassNotFoundException ex) {
           System.out.println(ex.getMessage());
       }
       return null;
    }
    public static Connection getConnection(String dbName, String dbPort, String dbURL, String dbUsername, String dbPassword) throws SQLException{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String URL = "jdbc:mysql://" + dbURL + ":" + dbPort + "/";
            return DriverManager.getConnection(URL+dbName, dbUsername, dbPassword);
        }catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
