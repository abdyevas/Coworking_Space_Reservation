import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import models.Reservations;

public class ReservationsTest {

    @Test
    public void givenNewReservation_WhenInitialized_ThenFieldsAreSetCorrectly() {
        Reservations reservation = new Reservations(1, "Sabina A", 101, "2025-01-25", "09:00", "17:00");

        assertEquals(1, reservation.getReservationID());
        assertEquals(101, reservation.getSpaceID());
        assertTrue(reservation.toString().contains("Sabina A"));
    }

    @Test
    public void givenReservation_WhenToStringCalled_ThenReturnsFormattedString() {
        Reservations reservation = new Reservations(2, "Kanan B", 202, "2025-02-15", "10:00", "14:00");

        String expectedString = "\nID: 2\nCustomer: Kanan B\nSpace ID: 202\nDate: 2025-02-15\nTime: 10:00 to 14:00\n";
        assertEquals(expectedString, reservation.toString());
    }
}
