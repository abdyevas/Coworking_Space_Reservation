package database;
import java.sql.Date;
import java.sql.*;
import java.util.*;

public class ReservationsDAO {
    public List<Map<String, Object>> getAllReservations() {
        List<Map<String, Object>> reservations = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM reservations")) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getInt("id"));
                row.put("customer_name", rs.getString("customer_name"));
                row.put("space_id", rs.getInt("space_id"));
                row.put("date", rs.getString("date"));
                row.put("start_time", rs.getString("start_time"));
                row.put("end_time", rs.getString("end_time"));
                reservations.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public void addReservation(String customerName, int spaceID, Date reservationDate, Time startTime, Time endTime) {
    String query = "INSERT INTO reservations (customer_name, space_id, reservation_date, start_time, end_time) VALUES (?, ?, ?, ?, ?)";
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, customerName);
        pstmt.setInt(2, spaceID);
        pstmt.setDate(3, reservationDate); 
        pstmt.setTime(4, startTime);      
        pstmt.setTime(5, endTime);        
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}   

    public void removeReservation(int reservationId) {
        String query = "DELETE FROM reservations WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, reservationId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
