import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection { // klasa ogólna, która służy do inicjacji połączenia z bazą danych

    // dane do połączenia
    private static final String HOST_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "bookshopdb";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void initDatabase() { // metoda inicjująca połączenie
        try (Connection conn = DriverManager.getConnection(HOST_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            System.out.println("Baza danych jest gotowa");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException { // metoda zwracająca połączenie z bazą
        String urlWithDB = HOST_URL+DB_NAME+"?serverTimezone=UTC";
        return DriverManager.getConnection(urlWithDB, USER, PASSWORD);
    }
}