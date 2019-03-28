package sk.itsovy.sk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Global {
    public static final int MAXITEMS=7;
    public static final String username="customer";
    public static final String password="123456";
    public static final String url = "jdbc:mysql://localhost:3306/kaufland";

    public static void println(String message){
        System.out.println(message);
    }
    public static void print(String message){
        System.out.print(message);
    }

    public static Connection getConnection(){
        Connection connection;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("DriverLoaded");
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }
}
