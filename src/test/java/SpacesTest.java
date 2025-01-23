import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import models.Spaces;

public class SpacesTest {

    @Test
    public void testSpaceInitialization() {
        Spaces space = new Spaces(1, "Conference Room", 100.0, true);

        assertEquals(1, space.getSpaceID());
        assertEquals(true, space.getAvailable());
    }

    @Test
    public void testSetAvailability() {
        Spaces space = new Spaces(2, "Private Office", 150.0, true);

        space.setAvailable(false);
        assertFalse(space.getAvailable());
    }

    @Test
    public void testToString() {
        Spaces space = new Spaces(3, "Open Desk", 50.0, true);

        String expectedString = "\nID: 3\nType: Open Desk\nPrice: 50.0 AZN\nAvailability: Available\n";
        assertEquals(expectedString, space.toString());
    }
}
