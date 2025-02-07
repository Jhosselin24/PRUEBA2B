import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:gestion_calificaciones.db";
    private static final String USER = "admin";
    private static final String PASS = "admin123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
