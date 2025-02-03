package database;

import java.sql.*;
import java.util.*;

public class SpacesDAO {
    public List<Map<String, Object>> getAllSpaces() {
        List<Map<String, Object>> spaces = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM spaces")) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getInt("id"));
                row.put("type", rs.getString("type"));
                row.put("price", rs.getDouble("price"));
                row.put("available", rs.getBoolean("available"));
                spaces.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spaces;
    }

    public void addSpace(String type, double price, boolean available) {
        String query = "INSERT INTO spaces (type, price, available) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, type);
            pstmt.setDouble(2, price);
            pstmt.setBoolean(3, available);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeSpace(int id) {
        String query = "DELETE FROM spaces WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
    
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSpaceAvailability(int id, boolean available) {
        String query = "UPDATE spaces SET available = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setBoolean(1, available);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
