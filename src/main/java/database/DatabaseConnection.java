package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/coworking_space";
    private static final String USER = "postgres";
    private static final String PASSWORD = "740Balqabaq";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL Driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        System.out.println("Successfully connected to the database!\n");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
