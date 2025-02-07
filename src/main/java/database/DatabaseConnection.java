package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static final String PROPERTIES_FILE = "src/main/resources/db.properties";
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            
            Properties properties = new Properties();
            try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
                properties.load(fis);
                URL = properties.getProperty("db.url");
                USER = properties.getProperty("db.user");
                PASSWORD = properties.getProperty("db.password");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL Driver not found", e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load database properties file", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        System.out.println("Successfully connected to the database!\n");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
