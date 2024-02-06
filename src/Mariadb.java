import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mariadb {

    public static void main(String[] args) {

        Connection connection = null;
        String url = "jdbc:mariadb://localhost:3306/test";
        String user = "root";
        String password = "password";

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Successfully connected to database.");
        
           
    }
}
