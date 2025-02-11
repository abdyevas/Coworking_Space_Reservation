import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import models.*;

public class ReservationsTest {

    @Test
    public void givenNewReservation_WhenInitialized_ThenFieldsAreSetCorrectly() {
        Spaces space = new Spaces("Conference Room", 100.0, true);
        Reservations reservation = new Reservations("Sabina A", space, LocalDate.parse("2025-01-25"), LocalTime.parse("09:00"), LocalTime.parse("11:00"));

        assertEquals("Conference Room", reservation.getSpace().getType());
        assertEquals(100, reservation.getSpace().getPrice());
        assertTrue(reservation.toString().contains("Sabina A"));
    }

    @Test
    public void givenReservation_WhenToStringCalled_ThenReturnsFormattedString() {
        Spaces space = new Spaces("Conference Room", 100.0, true);
        Reservations reservation = new Reservations("Kanan B", space, LocalDate.parse("2025-02-15"), LocalTime.parse("10:00"), LocalTime.parse("14:00"));

        String reservationString = reservation.toString();
    
        assertTrue(reservationString.contains("Customer: Kanan B"));
        assertTrue(reservationString.contains("Date: 2025-02-15"));
        assertTrue(reservationString.contains("Time: 10:00 to 14:00"));
    }
}
