import models.Spaces;
import models.Reservations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.InvalidSpaceIDException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {
    private Admin admin;
    private ArrayList<Spaces> spaces;
    private ArrayList<Reservations> reservations;

    @BeforeEach
    void setUp() {
        spaces = new ArrayList<>();
        reservations = new ArrayList<>();

        spaces.add(new Spaces(1, "Open Desk", 15.0, true));
        spaces.add(new Spaces(2, "Private Office", 50.0, true));

        reservations.add(new Reservations(1, "Sabina A", 1, "25-01-2025", "10:00", "12:00"));

        admin = new Admin(spaces, reservations);
    }

    @Test
    void givenValidSpace_whenAddCoworkingSpace_thenSpaceIsAdded() {
        int initialSize = spaces.size();

        admin.addCoworkingSpace("Shared Table", 20.0);

        assertEquals(initialSize + 1, spaces.size());
        assertEquals(initialSize + 1, spaces.get(initialSize).getSpaceID());
        assertTrue(spaces.get(initialSize).getAvailable());
    }

    @Test
    void givenValidSpaceID_whenRemoveCoworkingSpace_thenSpaceIsRemoved() {
        int validId = 1;

        assertTrue(spaces.stream().anyMatch(space -> space.getSpaceID() == validId));

        try {
            admin.removeCoworkingSpace(validId);
        } catch (Exception e) {
            fail("Invalid ID.");
        }

        assertTrue(spaces.stream().noneMatch(space -> space.getSpaceID() == validId));
    }

    @Test
    void givenInvalidSpaceID_whenRemoveCoworkingSpace_thenExceptionIsThrown() {
        int invalidId = 99; 

        try {
            admin.removeCoworkingSpace(invalidId);
            fail("Invalid ID.");
        } catch (InvalidSpaceIDException e) {
            assertEquals("Space ID not found: 99\n", e.getMessage());
        }
    }

    @Test
    void givenReservations_whenViewAllReservations_thenReservationsAreDisplayed() {
        admin.viewAllReservations();

        assertEquals(1, reservations.size());
        assertEquals("Sabina A", reservations.get(0).getCustomerName());
    }

    @Test
    void givenNoReservations_whenViewAllReservations_thenNoReservationsMessageIsDisplayed() {
        reservations.clear();
        admin.viewAllReservations();

        assertTrue(reservations.isEmpty());
    }
    
    @Test
    void givenValidClass_whenLoadCustomClass_thenClassIsLoadedSuccessfully() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        String validClassPath = "java/";
        String validClassName = "models.Spaces";  
        Admin.loadCustomClass(validClassPath, validClassName);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("Class " + validClassName + " loaded successfully!"));
    }

    @Test
    void givenInvalidClass_whenLoadCustomClass_thenExceptionMessageIsPrinted() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        String validClassPath = "java/";
        String invalidClassName = "models.InvalidClass";  
        Admin.loadCustomClass(validClassPath, invalidClassName);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("ClassNotFoundException") || output.contains("Error loading the class"));
    }
}
