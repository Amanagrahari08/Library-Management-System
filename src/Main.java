import ui.LoginPage;
import util.DatabaseConnection;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        // Test Database Connection
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            System.out.println("🎉 Connection Successful!");
        } else {
            System.out.println("⚠ Connection Failed!");
        }
       new LoginPage();
    }
}
